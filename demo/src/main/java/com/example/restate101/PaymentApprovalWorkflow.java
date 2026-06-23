package com.example.restate101;

import com.example.restate101.clients.ApprovalClient;
import com.example.restate101.model.PaymentRequest;
import dev.restate.sdk.SharedWorkflowContext;
import dev.restate.sdk.WorkflowContext;
import dev.restate.sdk.annotation.Shared;
import dev.restate.sdk.annotation.Workflow;
import dev.restate.sdk.common.DurablePromiseKey;
import dev.restate.sdk.common.TimeoutException;
import java.time.Duration;

/**
 * BUILDING BLOCK 3 — a Workflow: a durable, long-running process keyed by an id
 * (here, the payment id).
 *
 * The run handler kicks off a large transfer, then BLOCKS waiting for a human to
 * approve it — for up to three days. The pending decision is a Durable Promise:
 * if the Restate server (or this process) restarts mid-wait, the workflow resumes
 * exactly where it left off. A separate @Shared handler resolves the promise when
 * the human acts. No polling, no database flag, no scheduler.
 */
@Workflow
public class PaymentApprovalWorkflow {

  private static final DurablePromiseKey<Boolean> DECISION =
      DurablePromiseKey.of("decision", Boolean.class);

  @Workflow
  public String run(WorkflowContext ctx, PaymentRequest req) {
    String paymentId = ctx.key();

    // Notify an approver. This downstream call is flaky; Restate retries it for us.
    ctx.run("notify approver", () -> ApprovalClient.requestApproval(paymentId, req));

    // Wait for a human decision — but auto-reject if nobody acts within 3 days.
    boolean approved;
    try {
      approved = ctx.promise(DECISION).future().await(Duration.ofDays(3));
    } catch (TimeoutException timedOut) {
      approved = false;
    }

    if (!approved) {
      return "REJECTED: transfer of " + req.amountCents() + " cents was declined";
    }
    return "APPROVED: transferred " + req.amountCents() + " cents from "
        + req.fromAccount() + " to " + req.toAccount();
  }

  /** Called by the approver (a person/UI) to resolve the pending decision. */
  @Shared
  public void decide(SharedWorkflowContext ctx, boolean approved) {
    ctx.promiseHandle(DECISION).resolve(approved);
  }
}
