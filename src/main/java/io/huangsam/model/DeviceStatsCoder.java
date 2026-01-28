package io.huangsam.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.coders.VarLongCoder;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Custom Coder for DeviceStats to handle serialization/deserialization.
 */
public class DeviceStatsCoder extends CustomCoder<DeviceStats> {

    private static final DeviceStatsCoder INSTANCE = new DeviceStatsCoder();
    private static final StringUtf8Coder STRING_CODER = StringUtf8Coder.of();
    private static final VarLongCoder LONG_CODER = VarLongCoder.of();

    public static DeviceStatsCoder of() {
        return INSTANCE;
    }

    @Override
    public void encode(DeviceStats value, @NonNull OutputStream outStream) throws IOException {
        assert value != null;
        STRING_CODER.encode(value.deviceId(), outStream);
        LONG_CODER.encode(value.eventCount(), outStream);
        LONG_CODER.encode(value.windowStart(), outStream);
        LONG_CODER.encode(value.windowEnd(), outStream);
    }

    @Override
    public DeviceStats decode(@NonNull InputStream inStream) throws IOException {
        String deviceId = STRING_CODER.decode(inStream);
        long eventCount = LONG_CODER.decode(inStream);
        long windowStart = LONG_CODER.decode(inStream);
        long windowEnd = LONG_CODER.decode(inStream);
        return new DeviceStats(deviceId, eventCount, windowStart, windowEnd);
    }
}
