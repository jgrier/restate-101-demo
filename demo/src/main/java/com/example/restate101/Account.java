package com.example.restate101;

import dev.restate.sdk.ObjectContext;
import dev.restate.sdk.SharedObjectContext;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.Shared;
import dev.restate.sdk.annotation.VirtualObject;
import dev.restate.sdk.common.StateKey;
import dev.restate.sdk.common.TerminalException;

/**
 * BUILDING BLOCK 2 — a Virtual Object: state + code, addressed by a key
 * (here, an account id, available as ctx.key()).
 *
 * Restate runs AT MOST ONE exclusive (@Handler) invocation per key at a time, so
 * deposit/withdraw are serialized automatically — no locks, and no read-then-write
 * data races. State is held durably by Restate itself, so there is no separate
 * database to provision. @Shared handlers are read-only and may run concurrently.
 */
@VirtualObject
public class Account {

  private static final StateKey<Long> BALANCE = StateKey.of("balance", Long.class);

  @Handler
  public long deposit(ObjectContext ctx, long amountCents) {
    long balance = ctx.get(BALANCE).orElse(0L) + amountCents;
    ctx.set(BALANCE, balance);
    return balance;
  }

  @Handler
  public long withdraw(ObjectContext ctx, long amountCents) {
    long balance = ctx.get(BALANCE).orElse(0L);
    if (amountCents > balance) {
      throw new TerminalException(
          "Insufficient funds: balance=" + balance + ", requested=" + amountCents);
    }
    balance -= amountCents;
    ctx.set(BALANCE, balance);
    return balance;
  }

  @Shared
  public long balance(SharedObjectContext ctx) {
    return ctx.get(BALANCE).orElse(0L);
  }
}
