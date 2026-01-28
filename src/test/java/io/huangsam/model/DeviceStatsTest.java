package io.huangsam.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for DeviceStats record class.
 */
public class DeviceStatsTest {

	@Test
	public void testDeviceStatsCreation() {
		DeviceStats stats = new DeviceStats("device-1", 10L, 1000L, 2000L);
		assertEquals("device-1", stats.deviceId());
		assertEquals(10L, stats.eventCount());
		assertEquals(1000L, stats.windowStart());
		assertEquals(2000L, stats.windowEnd());
	}

	@Test
	public void testDeviceStatsEquality() {
		DeviceStats stats1 = new DeviceStats("device-1", 10L, 1000L, 2000L);
		DeviceStats stats2 = new DeviceStats("device-1", 10L, 1000L, 2000L);
		DeviceStats stats3 = new DeviceStats("device-2", 10L, 1000L, 2000L);

		assertEquals(stats1, stats2);
		assertNotEquals(stats1, stats3);
	}

	@Test
	public void testDeviceStatsHashCode() {
		DeviceStats stats1 = new DeviceStats("device-1", 10L, 1000L, 2000L);
		DeviceStats stats2 = new DeviceStats("device-1", 10L, 1000L, 2000L);
		DeviceStats stats3 = new DeviceStats("device-2", 10L, 1000L, 2000L);

		assertEquals(stats1.hashCode(), stats2.hashCode());
		assertNotEquals(stats1.hashCode(), stats3.hashCode());
	}

	@Test
	public void testDeviceStatsToString() {
		DeviceStats stats = new DeviceStats("device-1", 10L, 1000L, 2000L);
		String expected = "DeviceStats[deviceId=device-1, eventCount=10, windowStart=1000, windowEnd=2000]";
		assertEquals(expected, stats.toString());
	}

	@Test
	public void testDeviceStatsWithZeroCounts() {
		DeviceStats stats = new DeviceStats("device-1", 0L, 0L, 0L);
		assertEquals("device-1", stats.deviceId());
		assertEquals(0L, stats.eventCount());
		assertEquals(0L, stats.windowStart());
		assertEquals(0L, stats.windowEnd());
	}

	@Test
	public void testDeviceStatsWithNullDeviceId() {
		DeviceStats stats = new DeviceStats(null, 5L, 1000L, 2000L);
		assertNull(stats.deviceId());
		assertEquals(5L, stats.eventCount());
	}

	@Test
	public void testDeviceStatsWithNegativeValues() {
		DeviceStats stats = new DeviceStats("device-1", -1L, -1000L, -2000L);
		assertEquals("device-1", stats.deviceId());
		assertEquals(-1L, stats.eventCount());
		assertEquals(-1000L, stats.windowStart());
		assertEquals(-2000L, stats.windowEnd());
	}
}
