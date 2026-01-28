package io.huangsam.functions;

import io.huangsam.model.DeviceEvent;
import org.apache.beam.sdk.transforms.SerializableFunction;

/**
 * Filters to keep only sensor-related events (excludes system events).
 */
public class SensorEventFilter implements SerializableFunction<DeviceEvent, Boolean> {

    @Override
    public Boolean apply(DeviceEvent event) {
        assert event != null;
        return event.payload().startsWith("SENSOR_READING");
    }
}
