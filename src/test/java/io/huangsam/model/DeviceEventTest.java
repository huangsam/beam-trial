package io.huangsam.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for DeviceEvent record class.
 */
public class DeviceEventTest {

	@Test
	public void testDeviceEventCreation() {
		DeviceEvent event = new DeviceEvent("device-1", "SENSOR_READING");
		assertEquals("device-1", event.id());
		assertEquals("SENSOR_READING", event.payload());
	}

	@Test
	public void testDeviceEventEquality() {
		DeviceEvent event1 = new DeviceEvent("device-1", "SENSOR_READING");
		DeviceEvent event2 = new DeviceEvent("device-1", "SENSOR_READING");
		DeviceEvent event3 = new DeviceEvent("device-2", "SENSOR_READING");

		assertEquals(event1, event2);
		assertNotEquals(event1, event3);
	}

	@Test
	public void testDeviceEventHashCode() {
		DeviceEvent event1 = new DeviceEvent("device-1", "SENSOR_READING");
		DeviceEvent event2 = new DeviceEvent("device-1", "SENSOR_READING");
		DeviceEvent event3 = new DeviceEvent("device-2", "SENSOR_READING");

		assertEquals(event1.hashCode(), event2.hashCode());
		assertNotEquals(event1.hashCode(), event3.hashCode());
	}

	@Test
	public void testDeviceEventToString() {
		DeviceEvent event = new DeviceEvent("device-1", "SENSOR_READING");
		String expected = "DeviceEvent[id=device-1, payload=SENSOR_READING]";
		assertEquals(expected, event.toString());
	}

	@Test
	public void testDeviceEventWithNullValues() {
		DeviceEvent event = new DeviceEvent(null, null);
		assertNull(event.id());
		assertNull(event.payload());
	}

	@Test
	public void testDeviceEventWithEmptyStrings() {
		DeviceEvent event = new DeviceEvent("", "");
		assertEquals("", event.id());
		assertEquals("", event.payload());
	}
}