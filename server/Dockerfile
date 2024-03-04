# Preparation steps
FROM openjdk:17 AS BUILD_IMAGE
ENV APP_HOME=/root/dev/tesseract/
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME

# Copy all the files
COPY ./build.gradle ./gradlew ./gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY ./src $APP_HOME/src/

# Build desirable JAR
RUN microdnf install findutils
RUN ./gradlew clean build -x test

FROM openjdk:17
WORKDIR /root/
COPY --from=BUILD_IMAGE '/root/dev/tesseract/build/libs/tesseract-0.0.1-SNAPSHOT.jar' '/app/tesseract.jar'
EXPOSE 8080
CMD ["java","-jar","/app/tesseract.jar"]