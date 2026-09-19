FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY *.java ./
COPY *.html ./
COPY *.css ./
COPY *.js ./

RUN javac HydroGuard.java HydroGuardServer.java

EXPOSE 8080

CMD ["java", "HydroGuardServer"]