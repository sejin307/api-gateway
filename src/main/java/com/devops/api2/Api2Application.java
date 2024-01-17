package com.devops.api2;

import com.devops.api2.gateway.locator.definition.BaseGatewayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * https://cloud.spring.io/spring-cloud-gateway/reference/html/
 * 시스템간 RESTAPI 인터페이스
 * 인증, API Gateway모듈
 * AUTHOR: sejin
 */
@SpringBootApplication
@EnableConfigurationProperties(BaseGatewayProperties.class)
public class Api2Application {
	public static void main(String[] args) {
		SpringApplication.run(Api2Application.class, args);
	}

	}

	/**
	 * 2023.07.03
	 * TODO: (개발)
	 * 		1.DB변경(H2 > MySQL)								o
	 *      2.토큰 갱신										o
	 *      3. REST API 핸들링 (DB스키마 설계,네트워크,서버구성)	o
	 *       WebFlux , Spring Cloud Gateway, Resillience4j  o
	 *
	 *
	 * TODO: (devOps)
	 *      1. GIT세팅 / 빌드세팅 o
	 *      2. Docker 세팅		o
	 *      3. ecs(c),eks(t)    o
	 *      4. 빌드배포테스트      o
	 *      5. ecs 고정ip 연결  o     / 도메인 부여  ing
	 *      #git token  >>         glpat-kApSyvMcKmYWyLYmj3VK
	 *                             glpat-sYSZvGSkFy1vk4jyxEYx
	 *
	 *http://43.203.69.182:8080/api-gw/api-gw.git
	 *glpat-uVRf8JJN3_ezastQox6i
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */