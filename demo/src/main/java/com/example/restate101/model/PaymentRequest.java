package com.example.restate101.model;

/** Input to PaymentApprovalWorkflow.run. Amount is in cents to keep it integer-safe. */
public record PaymentRequest(String fromAccount, String toAccount, long amountCents, String memo) {}
