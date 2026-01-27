package io.huangsam.functions;

import io.huangsam.model.DeviceEvent;
import org.apache.beam.sdk.transforms.DoFn;

/**
 * Generates DeviceEvent objects from sequence numbers.
 */
public class DeviceEventGenerator extends DoFn<Long, DeviceEvent> {

    @ProcessElement
    public void processElement(@Element Long sequence, OutputReceiver<DeviceEvent> out) {
        // Generate different types of events based on sequence
        String id = "device-" + (sequence % 3 + 1); // devices 1,2,3
        String payload;
        if (sequence % 10 == 0) {
            payload = "SYSTEM_EVENT"; // system event
        } else if (sequence % 5 == 0) {
            payload = "SENSOR_ERROR_" + sequence; // error event (every 5th)
        } else {
            payload = "SENSOR_READING_" + sequence; // sensor event
        }
        out.output(new DeviceEvent(id, payload));
    }
}
