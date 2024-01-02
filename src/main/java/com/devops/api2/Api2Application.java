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
	 * TODO:
	 *  화면터치로 모바일 디바이스를 제어한다는 개념이 없었던시절, iPhone 이 처음 나왔을때
	 *  어떻게 디바이스를 조작하나요? pda에 사용하던 포인터?  i펜슬?
	 *  누구나 태어날때부터 가지고있는 손가락으로 컨트롤 합니다.
	 *  인스타그램은 쉽고 빠르게 사진을전송한다는 뜻을 가지고있음.
	 *  이처럼 누구나 가지고있는 데이터를 바탕으로 손쉽게 커뮤니케이션할수있고 공유할수있는 비즈니스 모델을 만들어야함
	 *
	 *  MindBlow
	 *  가족앨범과 동영상을 자동으로 생성하는 어플리케이션
	 *  소장하고싶은 동영상과 앨범을 자동으로 생성
	 *  AI가 만들어주는 멋진 효과와 적절한자막을 넣어 자동생성
	 *  굳이 소장하지않더라도 어플을 사용하면서 재미를 추구할수있어야함.
	 *  스노우? B612?
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
	 *
	 */