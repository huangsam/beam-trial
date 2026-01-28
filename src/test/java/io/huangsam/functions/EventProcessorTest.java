package io.huangsam.functions;

import io.huangsam.model.DeviceEvent;
import io.huangsam.model.DeviceEventCoder;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for EventProcessor DoFn.
 */
public class EventProcessorTest {

    @Rule
    public final TestPipeline pipeline = TestPipeline.create();

    @Test
    public void testEventProcessing() {
        List<DeviceEvent> events = Arrays.asList(
                new DeviceEvent("device-1", "SENSOR_READING_1"),
                new DeviceEvent("device-2", "SENSOR_ERROR_5"),
                new DeviceEvent("device-3", "SENSOR_READING_2"),
                new DeviceEvent("device-4", "SYSTEM_EVENT")
        );

        TupleTag<DeviceEvent> mainTag = new TupleTag<>("main-output");
        PCollectionTuple results = pipeline
                .apply(Create.of(events).withCoder(DeviceEventCoder.of()))
                .apply(ParDo.of(new EventProcessor())
                        .withOutputTags(mainTag, TupleTagList.of(EventProcessor.ERROR_EVENTS)));

        PCollection<DeviceEvent> processedEvents = results.get(mainTag).setCoder(DeviceEventCoder.of());
        PCollection<DeviceEvent> errorEvents = results.get(EventProcessor.ERROR_EVENTS).setCoder(DeviceEventCoder.of());

        PAssert.that(processedEvents)
                .containsInAnyOrder(
                        new DeviceEvent("device-1", "SENSOR_READING_1"),
                        new DeviceEvent("device-3", "SENSOR_READING_2"),
                        new DeviceEvent("device-4", "SYSTEM_EVENT")
                );

        PAssert.that(errorEvents)
                .containsInAnyOrder(
                        new DeviceEvent("device-2", "SENSOR_ERROR_5")
                );

        pipeline.run();
    }
}
