FROM amazoncorretto:17
#######################################################################
# 패키지 업데이트 (Amazon Linux를 기반으로 한 경우)
RUN yum update -y

ARG JAR_FILE
COPY  /build/libs/api-gw-0.0.1-SNAPSHOT.jar ./api-gw.jar
ENTRYPOINT ["java", "-jar", "/api-gw.jar"]