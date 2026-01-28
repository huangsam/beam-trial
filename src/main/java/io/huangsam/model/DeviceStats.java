package io.huangsam.model;

/**
 * Represents device statistics for a time window.
 */
public record DeviceStats(String deviceId, long eventCount, long windowStart, long windowEnd) {

    @Override
    public String toString() {
        return "DeviceStats{deviceId='" + deviceId + "', eventCount=" + eventCount +
                ", windowStart=" + windowStart + ", windowEnd=" + windowEnd + "}";
    }
}
