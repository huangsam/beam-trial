package io.huangsam.functions;

import io.huangsam.model.DeviceEvent;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.TupleTag;

/**
 * Processes events, routing error events to side output and valid events to
 * main output.
 */
public class EventProcessor extends DoFn<DeviceEvent, DeviceEvent> {

    public static final TupleTag<DeviceEvent> ERROR_EVENTS = new TupleTag<>("error-events");

    @ProcessElement
    public void processElement(@Element DeviceEvent event, ProcessContext c) {
        // Route error events to side output
        if (event.payload().contains("ERROR")) {
            c.output(ERROR_EVENTS, event);
        } else {
            // Pass through valid events to main output
            c.output(event);
        }
    }
}
