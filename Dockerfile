FROM amazoncorretto:17
#######################################################################
ARG JAR_FILE
COPY  /build/libs/api-gw-0.0.1-SNAPSHOT.jar ./api-gw.jar
ENTRYPOINT ["java", "-jar", "/api-gw.jar"]