package com.example.restate101;

import com.example.restate101.clients.PaymentClient;
import com.example.restate101.clients.UserClient;
import com.example.restate101.model.SubscriptionRequest;
import dev.restate.sdk.Context;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.Service;
import dev.restate.sdk.common.TerminalException;
import java.util.ArrayList;
import java.util.List;

/**
 * BUILDING BLOCK 1 — a Service: a plain durable function.
 *
 * Each ctx.run(...) is a durable step: its result is journaled, it is retried on
 * transient failure, and on replay it is NOT re-executed. If a step fails
 * permanently (TerminalException), we run the compensations we collected so far,
 * in reverse — i.e. a Saga. No queue, no state machine, no dead-letter table.
 */
@Service
public class SubscriptionService {

  @Handler
  public void add(Context ctx, SubscriptionRequest req) {
    List<Runnable> compensations = new ArrayList<>();

    try {
      // 1. Create the customer record (and remember how to undo it).
      compensations.add(() -> ctx.run("undo: delete user", () -> UserClient.delete(req.userId())));
      ctx.run("create user", () -> UserClient.create(req.userId(), req.creditCard()));

      // 2. Activate each subscription with the payment provider.
      for (String plan : req.subscriptions()) {
        compensations.add(() -> ctx.run("undo: cancel " + plan, () -> PaymentClient.cancel(req.userId(), plan)));
        ctx.run("subscribe: " + plan, () -> PaymentClient.subscribe(req.userId(), req.creditCard(), plan));
      }
    } catch (TerminalException e) {
      // Undo everything done so far, newest first. This runs durably too.
      for (int i = compensations.size() - 1; i >= 0; i--) {
        compensations.get(i).run();
      }
      throw e;
    }
  }
}
