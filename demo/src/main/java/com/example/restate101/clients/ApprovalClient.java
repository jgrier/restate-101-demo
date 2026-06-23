package com.example.restate101.clients;

import com.example.restate101.model.PaymentRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Stand-in for a flaky downstream "notify the approver" system. */
public final class ApprovalClient {
  private static final Logger log = LogManager.getLogger(ApprovalClient.class);
  private static final ConcurrentHashMap<String, AtomicInteger> attempts = new ConcurrentHashMap<>();

  /**
   * Pretend to call a notification system that fails its first two attempts.
   * Because this runs inside ctx.run(...), Restate retries it automatically with
   * backoff until it succeeds — then records the success in the journal so it
   * never runs again on replay. (A plain RuntimeException is retryable; only a
   * TerminalException would stop the retries.)
   */
  public static void requestApproval(String paymentId, PaymentRequest req) {
    int attempt = attempts.computeIfAbsent(paymentId, k -> new AtomicInteger()).incrementAndGet();
    log.info("Notifying approver for {} (attempt #{})", paymentId, attempt);
    if (attempt < 3) {
      throw new RuntimeException("approval system temporarily unavailable (attempt " + attempt + ")");
    }
    log.info("Approver notified for {}: transfer {} cents {} -> {}",
        paymentId, req.amountCents(), req.fromAccount(), req.toAccount());
  }

  private ApprovalClient() {}
}
