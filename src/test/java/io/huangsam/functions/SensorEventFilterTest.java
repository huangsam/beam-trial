package io.huangsam.functions;

import io.huangsam.model.DeviceEvent;
import io.huangsam.model.DeviceEventCoder;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for SensorEventFilter.
 */
public class SensorEventFilterTest {

    @Rule
    public final TestPipeline pipeline = TestPipeline.create();

    @Test
    public void testSensorEventFiltering() {
        List<DeviceEvent> events = Arrays.asList(
                new DeviceEvent("device-1", "SENSOR_READING_1"),
                new DeviceEvent("device-2", "SYSTEM_EVENT"),
                new DeviceEvent("device-3", "SENSOR_READING_2"),
                new DeviceEvent("device-4", "SENSOR_ERROR_5")
        );

        PCollection<DeviceEvent> sensorEvents = pipeline
                .apply(Create.of(events).withCoder(DeviceEventCoder.of()))
                .apply(Filter.by(new SensorEventFilter()))
                .setCoder(DeviceEventCoder.of());

        PAssert.that(sensorEvents)
                .containsInAnyOrder(
                        new DeviceEvent("device-1", "SENSOR_READING_1"),
                        new DeviceEvent("device-3", "SENSOR_READING_2")
                );

        pipeline.run();
    }
}
