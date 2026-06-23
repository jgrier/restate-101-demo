package com.example.restate101.model;

import java.util.List;

/** Input to SubscriptionService.add. Plain record, serialized as JSON by the SDK. */
public record SubscriptionRequest(String userId, String creditCard, List<String> subscriptions) {}
