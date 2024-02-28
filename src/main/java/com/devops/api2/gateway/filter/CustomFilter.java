package com.devops.api2.gateway.filter;

import com.devops.api2.gateway.model.GatewayLog;
import com.devops.api2.gateway.repository.GatewayLogRepository;
import com.devops.api2.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 라우팅 서비스에서 공통으로 활용하는 필터
 * TODO:Retry 설정 Cache에서 기존 요청정보를 가져와서 call하면 성능향상, 필요에따라 구현
 */
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    @Autowired
    private GatewayLogRepository gatewayLogRepository;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public CustomFilter(TokenProvider tokenProvider) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
    }

    /**
     * 여기서 실제 filter 로직 실행
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        //before-processing (서비스 시작시 세팅)
        return (exchange, chain) -> {
            //pre-processing (Route start 할때)
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        // post-processing (Route end 이후)
                        String jwt = resolveToken(exchange);
                        String hostName = "";
                        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                            hostName = authentication.getName();
                        }

                        String requestId = exchange.getRequest().getId();
                        String requestPath = exchange.getRequest().getPath().toString();
                        Integer responseStatus = exchange.getResponse().getStatusCode().value();
                        String remoteAddress = exchange.getRequest().getRemoteAddress().getAddress().toString();
                        AtomicReference<String> requestBodyParam = new AtomicReference<>((String) exchange.getAttributes().get("requestBodyParam"));// TODO:이게안댐..

                        // 참조 : https://velog.io/@aaa6400/Spring-Webflux-%EB%A6%AC%ED%80%98%EC%8A%A4%ED%8A%B8%EB%B0%94%EB%94%94-%EC%BA%90%EC%8B%B1%ED%95%98%EA%B8%B0
                        // 운영배포시 gateway_logs 테이블 request_bodyparam 컬럼 타입 mediumtext 변경 필수 !!!!!! by jwchu
                        exchange.getRequest()
                                .getBody()
                                .map(dataBuffer -> {
                                    final byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                    DataBufferUtils.release(dataBuffer.read(bytes));
                                    requestBodyParam.set(new String(bytes));
                                    return new String(bytes);})
                                .subscribe();

                        //231018 Request param, body, method 추가 sejin
                        String requestQueryParam = exchange.getRequest().getQueryParams().toString();

                        String requestMethod = exchange.getRequest().getMethod().toString();
                        String requestHeader = exchange.getRequest().getHeaders().toString();

                        //개발서버 로그 insert 안되도록
                        saveLogToDb(requestId, requestPath, requestQueryParam, requestBodyParam.get(), requestMethod, requestHeader, responseStatus, remoteAddress, hostName);
                    }));
        };
    }

    public static class Config {
        //Config 설정
    }

    private void saveLogToDb(String requestId, String requestPath,String requestQueryParam, String requestBodyParam,
                             String requestMethod, String requestHeader, Integer responseStatus, String remoteAddress, String hostName) {
        GatewayLog gatewayLog = new GatewayLog();
        gatewayLog.setRequestId(requestId);
        gatewayLog.setRequestPath(requestPath);
        gatewayLog.setRequestQueryParam(requestQueryParam);
        gatewayLog.setRequestBodyParam(requestBodyParam);
        gatewayLog.setRequestMethod(requestMethod);
        gatewayLog.setRequestHeader(requestHeader);
        gatewayLog.setResponseStatus(responseStatus);
        gatewayLog.setRemoteAddress(remoteAddress);
        gatewayLog.setHostName(hostName);

        gatewayLogRepository.save(gatewayLog);
    }


    private String resolveToken(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

