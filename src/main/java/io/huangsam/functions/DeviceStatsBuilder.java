package io.huangsam.functions;

import io.huangsam.model.DeviceStats;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.windowing.IntervalWindow;
import org.apache.beam.sdk.values.KV;

/**
 * Converts KV<String, Long> pairs into DeviceStats objects,
 * extracting window timing information.
 */
public class DeviceStatsBuilder extends DoFn<KV<String, Long>, DeviceStats> {
    @ProcessElement
    public void processElement(@Element KV<String, Long> kv, IntervalWindow window, ProcessContext c) {
        String deviceId = kv.getKey();
        long eventCount = kv.getValue();
        long windowStart = window.start().getMillis();
        long windowEnd = window.end().getMillis();

        DeviceStats stats = new DeviceStats(deviceId, eventCount, windowStart, windowEnd);
        c.output(stats);
    }
}
