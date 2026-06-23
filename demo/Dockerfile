# Build the Java service with JDK 17 (matches the Gradle toolchain in build.gradle.kts),
# so nothing depends on whatever JDK is installed on the host.
FROM gradle:8.14-jdk17 AS build
WORKDIR /app
COPY settings.gradle.kts build.gradle.kts ./
COPY src ./src
RUN gradle --no-daemon --no-build-cache installDist

# Slim runtime image — just a JRE + the built distribution.
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/install/restate-101-demo ./
EXPOSE 9080
# Listens on 0.0.0.0:9080; the Restate container reaches it at http://demo:9080.
CMD ["./bin/restate-101-demo"]
