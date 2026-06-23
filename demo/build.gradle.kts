plugins {
  java
  application
}

repositories {
  mavenCentral()
}

// Restate Java SDK. Server 1.7.x pairs with SDK 2.8.x (June 2026).
val restateVersion = "2.8.0"

dependencies {
  // Generates the typed *Client classes from the @Service/@VirtualObject/@Workflow annotations.
  annotationProcessor("dev.restate:sdk-api-gen:$restateVersion")

  // The SDK + the built-in HTTP server. This single artifact pulls in sdk-api + sdk-http-vertx.
  implementation("dev.restate:sdk-java-http:$restateVersion")

  // Logging backend (the SDK logs through log4j2).
  implementation("org.apache.logging.log4j:log4j-core:2.24.1")
}

java {
  toolchain {
    // 17 is fine. Use 21+ if you want Restate's virtual-thread executor.
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

application {
  mainClass.set("com.example.restate101.Main")
}
