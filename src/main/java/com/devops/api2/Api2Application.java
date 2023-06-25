package com.devops.api2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	 * TODO: (개발)
	 * 		1.DB변경(H2 > MySQL)
	 *      2.토큰 갱신
	 *      3.juul proxy 추가
	 *      4. REST API 핸들링 (DB스키마 설계,네트워크,서버구성)
	 *
	 * TODO: (devOps)
	 *      1. GIT세팅 / 빌드세팅
	 *      2. Docker 세팅
	 *      3. k8s
	 *      4. 빌드배포테스트
	 */

}
