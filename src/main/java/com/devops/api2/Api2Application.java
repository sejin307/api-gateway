package com.devops.api2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 시스템간 RESTAPI 인터페이스
 * 인증, API Proxy서버  api2
 * AUTHOR: sejin
 */
@SpringBootApplication
public class Api2Application {

	public static void main(String[] args) {
		SpringApplication.run(Api2Application.class, args);
	}

	/**
	 * 2023.07.03
	 * TODO: (개발)
	 * 		1.DB변경(H2 > MySQL)								o
	 *      2.토큰 갱신										o
	 *      3. REST API 핸들링 (DB스키마 설계,네트워크,서버구성)
	 *          WebFlux , Spring Cloud Gateway, Resillience4j  ING
	 *
	 *
	 * TODO: (devOps)
	 *      1. GIT세팅 / 빌드세팅 o
	 *      2. Docker 세팅		o
	 *      3. ecs(c),eks(t)    o
	 *      4. 빌드배포테스트      o
	 *      5. ecs 고정ip 연결  o     / 도메인 부여  ing
	 */

}
