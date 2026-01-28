package io.huangsam.functions;

import io.huangsam.model.DeviceStats;
import io.huangsam.model.DeviceStatsCoder;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.junit.Rule;
import org.junit.Test;

import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Duration;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for DeviceStatsBuilder DoFn.
 */
public class DeviceStatsBuilderTest {

	@Rule
	public final TestPipeline pipeline = TestPipeline.create();

	@Test
	public void testDeviceStatsBuilding() {
		List<KV<String, Long>> kvPairs = Arrays.asList(KV.of("device-1", 10L), KV.of("device-2", 15L),
				KV.of("device-3", 8L));

		PCollection<DeviceStats> stats = pipeline.apply(Create.of(kvPairs))
				.apply(Window.into(FixedWindows.of(Duration.standardSeconds(5))))
				.apply(ParDo.of(new DeviceStatsBuilder())).setCoder(DeviceStatsCoder.of());

		// Since we're using windowing, we need to check that stats are created
		// The exact window bounds depend on the test execution timing
		PAssert.that(stats).satisfies((Iterable<DeviceStats> statsList) -> {
			boolean foundDevice1 = false, foundDevice2 = false, foundDevice3 = false;
			for (DeviceStats stat : statsList) {
				if ("device-1".equals(stat.deviceId()) && stat.eventCount() == 10L) {
					foundDevice1 = true;
				} else if ("device-2".equals(stat.deviceId()) && stat.eventCount() == 15L) {
					foundDevice2 = true;
				} else if ("device-3".equals(stat.deviceId()) && stat.eventCount() == 8L) {
					foundDevice3 = true;
				}
			}
			if (!(foundDevice1 && foundDevice2 && foundDevice3)) {
				throw new AssertionError("Missing expected stats");
			}
			return null;
		});

		pipeline.run();
	}
}
