package io.huangsam.functions;

import io.huangsam.model.DeviceEvent;
import io.huangsam.model.DeviceEventCoder;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for DeviceEventGenerator DoFn.
 */
public class DeviceEventGeneratorTest {

	@Rule
	public final TestPipeline pipeline = TestPipeline.create();

	@Test
	public void testDeviceEventGeneration() {
		List<Long> sequences = Arrays.asList(1L, 5L, 10L, 15L);

		PCollection<DeviceEvent> events = pipeline.apply(Create.of(sequences))
				.apply(ParDo.of(new DeviceEventGenerator())).setCoder(DeviceEventCoder.of());

		PAssert.that(events).containsInAnyOrder(new DeviceEvent("device-2", "SENSOR_READING_1"),
				new DeviceEvent("device-3", "SENSOR_ERROR_5"), new DeviceEvent("device-2", "SYSTEM_EVENT"),
				new DeviceEvent("device-1", "SENSOR_ERROR_15"));

		pipeline.run();
	}
}
