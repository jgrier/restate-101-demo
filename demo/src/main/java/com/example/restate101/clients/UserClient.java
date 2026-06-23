package com.example.restate101.clients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Stand-in for a real "customer records" downstream system. */
public final class UserClient {
  private static final Logger log = LogManager.getLogger(UserClient.class);

  public static void create(String userId, String creditCard) {
    log.info("Creating user {} with card ****{}", userId, last4(creditCard));
  }

  public static void delete(String userId) {
    log.info("COMPENSATION: deleting user {}", userId);
  }

  private static String last4(String card) {
    return (card == null || card.length() < 4) ? "????" : card.substring(card.length() - 4);
  }

  private UserClient() {}
}
