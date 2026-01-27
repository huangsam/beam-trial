package io.huangsam.model;

/**
 * Represents device statistics for a time window.
 */
public class DeviceStats {
    private final String deviceId;
    private final long eventCount;
    private final long windowStart;
    private final long windowEnd;

    public DeviceStats(String deviceId, long eventCount, long windowStart, long windowEnd) {
        this.deviceId = deviceId;
        this.eventCount = eventCount;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
    }

    public String deviceId() {
        return deviceId;
    }

    public long eventCount() {
        return eventCount;
    }

    public long windowStart() {
        return windowStart;
    }

    public long windowEnd() {
        return windowEnd;
    }

    @Override
    public String toString() {
        return "DeviceStats{deviceId='" + deviceId + "', eventCount=" + eventCount +
               ", windowStart=" + windowStart + ", windowEnd=" + windowEnd + "}";
    }
}
