package me.endcrystal.pvpswitch.timer.model;


import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

public class PlayerTimerSwitch {

    // this useful for changing constant if some player needed more time
    private final static long CONSTANT = 59;
    private long expiryTimeConstantSeconds = CONSTANT;

    private Instant expiry;


    public PlayerTimerSwitch(@NotNull Long expiryTimeConstantSeconds) {
        --expiryTimeConstantSeconds;
        if (expiryTimeConstantSeconds < 0) {
            expiryTimeConstantSeconds = 0L;
        }
        this.expiryTimeConstantSeconds = expiryTimeConstantSeconds;
        this.expiry = Instant.now().plusNanos(expiryTimeConstantSeconds);
    }

    public PlayerTimerSwitch() {
        this.expiry = Instant.now().plusNanos(expiryTimeConstantSeconds);
    }

    public void updateExpiry(@NotNull Long expiryTimeConstantSeconds) {
        --expiryTimeConstantSeconds;
        if (expiryTimeConstantSeconds < 0) {
            expiryTimeConstantSeconds = 0L;
        }
        this.expiryTimeConstantSeconds = expiryTimeConstantSeconds;
        expiry = Instant.now().plusNanos(this.expiryTimeConstantSeconds);
    }

    public void updateExpiry() {
        if (expiryTimeConstantSeconds != CONSTANT)
            expiryTimeConstantSeconds = CONSTANT;
        expiry = Instant.now().plusNanos(this.expiryTimeConstantSeconds);
    }

    public PlayerTimerSwitch expiredByDefault() {
        expiry = Instant.now().plusSeconds(-expiryTimeConstantSeconds);
        return this;
    }

    public boolean isExpired() {
        return Duration.between(expiry, Instant.now()).getSeconds() > this.expiryTimeConstantSeconds;
    }

    public long leftSeconds() {
        long leftSeconds = this.expiryTimeConstantSeconds - Duration.between(expiry, Instant.now()).getSeconds();
        return leftSeconds < 0 ? 0 : leftSeconds;
    }


}
