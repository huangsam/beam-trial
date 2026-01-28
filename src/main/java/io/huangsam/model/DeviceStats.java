package io.huangsam.model;

/**
 * Represents device statistics for a time window.
 */
public record DeviceStats(String deviceId, long eventCount, long windowStart, long windowEnd) {}
