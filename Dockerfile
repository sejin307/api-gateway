FROM amazoncorretto:17
#######################################################################
ARG JAR_FILE
COPY  /build/libs/api2-auth-0.0.1-SNAPSHOT.jar ./api2-auth.jar
ENTRYPOINT ["java", "-jar", "/api2-auth-gateway.jar"]