FROM amazoncorretto:17
#######################################################################
# 패키지 업데이트 / ECR 이미지스캔할때 보안취약레포트 해결을 위함.
RUN apt-get update

ARG JAR_FILE
COPY  /build/libs/api-gw-0.0.1-SNAPSHOT.jar ./api-gw.jar
ENTRYPOINT ["java", "-jar", "/api-gw.jar"]
