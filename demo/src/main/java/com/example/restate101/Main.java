package com.example.restate101;

import dev.restate.sdk.endpoint.Endpoint;
import dev.restate.sdk.http.vertx.RestateHttpServer;

/**
 * Boots an HTTP endpoint that hosts our three handlers and serves them to the
 * Restate server. There is no framework, no message broker, no database wiring
 * here — just "here are my services, run them."
 *
 * Start this (./gradlew run), then point Restate at it:
 *   restate-server
 *   restate deployments register http://localhost:9080
 */
public class Main {
  public static void main(String[] args) {
    RestateHttpServer.listen(
        Endpoint.bind(new SubscriptionService())
            .bind(new Account())
            .bind(new PaymentApprovalWorkflow()),
        9080);
  }
}
