package io.huangsam.model;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;

/**
 * Unit tests for DeviceEventCoder.
 */
public class DeviceEventCoderTest {

    @Test
    public void testDeviceEventCoderRoundTrip() throws IOException {
        DeviceEvent original = new DeviceEvent("device-1", "SENSOR_READING");
        DeviceEventCoder coder = DeviceEventCoder.of();

        // Encode
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        coder.encode(original, out);

        // Decode
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DeviceEvent decoded = coder.decode(in);

        assert decoded != null;
        assertEquals(original, decoded);
        assertEquals(original.id(), decoded.id());
        assertEquals(original.payload(), decoded.payload());
    }

    @Test
    public void testDeviceEventCoderWithEmptyStrings() throws IOException {
        DeviceEvent original = new DeviceEvent("", "");
        DeviceEventCoder coder = DeviceEventCoder.of();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        coder.encode(original, out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DeviceEvent decoded = coder.decode(in);

        assertEquals(original, decoded);
    }

    @Test
    public void testDeviceEventCoderWithSpecialCharacters() throws IOException {
        DeviceEvent original = new DeviceEvent("device-特殊字符", "payload-ñáéíóú");
        DeviceEventCoder coder = DeviceEventCoder.of();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        coder.encode(original, out);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DeviceEvent decoded = coder.decode(in);

        assertEquals(original, decoded);
    }

    @Test
    public void testDeviceEventCoderSingleton() {
        DeviceEventCoder coder1 = DeviceEventCoder.of();
        DeviceEventCoder coder2 = DeviceEventCoder.of();

        assertSame(coder1, coder2);
    }
}
