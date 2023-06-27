FROM amazoncorretto:17

## root user
#ENV JAVA_OPTS="-XX:MinRAMPercentage=50.0 -XX:MaxRAMPercentage=80.0"
#ENV TZ=DOCKER_TIMEZONE
#ENV LANG=C.UTF-8
#ENV LANGUAGE=C.UTF-8
ARG JAR_FILE
COPY /var/lib/jenkins/workspace/api2-auth/build/libsapi2-auth-0.0.1-SNAPSHOT.jar /api2-auth.jar
ENTRYPOINT ["java", "-jar", "/api2-auth.jar"]