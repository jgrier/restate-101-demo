package com.example.restate101.clients;

import dev.restate.sdk.common.TerminalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Stand-in for a payment provider. */
public final class PaymentClient {
  private static final Logger log = LogManager.getLogger(PaymentClient.class);

  /**
   * Activate a subscription. The plan named "blocked" simulates a permanent,
   * non-retryable business failure (e.g. compliance rejection) — we throw a
   * TerminalException so Restate stops retrying and lets the Saga compensate.
   */
  public static String subscribe(String userId, String creditCard, String plan) {
    if ("blocked".equalsIgnoreCase(plan)) {
      throw new TerminalException("Plan '" + plan + "' is not permitted for this account");
    }
    String paymentId = "pay-" + userId + "-" + plan;
    log.info("Subscribed {} to {} (paymentId={})", userId, plan, paymentId);
    return paymentId;
  }

  public static void cancel(String userId, String plan) {
    log.info("COMPENSATION: cancelling {} subscription for {}", plan, userId);
  }

  private PaymentClient() {}
}
