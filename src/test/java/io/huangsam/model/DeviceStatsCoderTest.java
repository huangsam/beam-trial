package io.huangsam.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Unit tests for DeviceStatsCoder.
 */
public class DeviceStatsCoderTest {

    @Test
    public void testDeviceStatsCoderRoundTrip() throws IOException {
        DeviceStats original = new DeviceStats("device-1", 10L, 1000L, 2000L);
        DeviceStatsCoder coder = DeviceStatsCoder.of();

        // Encode
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        coder.encode(original, out);

        // Decode
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DeviceStats decoded = coder.decode(in);

        assert decoded != null;
        assertEquals(original, decoded);
        assertEquals(original.deviceId(), decoded.deviceId());
        assertEquals(original.eventCount(), decoded.eventCount());
        assertEquals(original.windowStart(), decoded.windowStart());
        assertEquals(original.windowEnd(), decoded.windowEnd());
    }

    @Test
    public void testDeviceStatsCoderWithZeroValues() throws IOException {
        DeviceStats original = new DeviceStats("device-1", 0L, 0L, 0L);
        DeviceStatsCoder coder = DeviceStatsCoder.of();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        coder.encode(original, out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DeviceStats decoded = coder.decode(in);

        assertEquals(original, decoded);
    }

    @Test
    public void testDeviceStatsCoderWithNegativeValues() throws IOException {
        DeviceStats original = new DeviceStats("device-1", -5L, -1000L, -2000L);
        DeviceStatsCoder coder = DeviceStatsCoder.of();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        coder.encode(original, out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DeviceStats decoded = coder.decode(in);

        assertEquals(original, decoded);
    }

    @Test
    public void testDeviceStatsCoderWithLargeValues() throws IOException {
        DeviceStats original = new DeviceStats("device-1", Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
        DeviceStatsCoder coder = DeviceStatsCoder.of();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        coder.encode(original, out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DeviceStats decoded = coder.decode(in);

        assertEquals(original, decoded);
    }

    @Test
    public void testDeviceStatsCoderSingleton() {
        DeviceStatsCoder coder1 = DeviceStatsCoder.of();
        DeviceStatsCoder coder2 = DeviceStatsCoder.of();

        assertSame(coder1, coder2);
    }
}
