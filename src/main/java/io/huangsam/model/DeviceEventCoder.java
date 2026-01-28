package io.huangsam.model;

import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Custom Coder for DeviceEvent to avoid SerializableCoder warnings.
 */
public class DeviceEventCoder extends CustomCoder<DeviceEvent> {

	private static final DeviceEventCoder INSTANCE = new DeviceEventCoder();
	private static final StringUtf8Coder STRING_CODER = StringUtf8Coder.of();

	public static DeviceEventCoder of() {
		return INSTANCE;
	}

	@Override
	public void encode(DeviceEvent value, @NonNull OutputStream outStream) throws IOException {
		assert value != null;
		STRING_CODER.encode(value.id(), outStream);
		STRING_CODER.encode(value.payload(), outStream);
	}

	@Override
	public DeviceEvent decode(@NonNull InputStream inStream) throws IOException {
		String id = STRING_CODER.decode(inStream);
		String payload = STRING_CODER.decode(inStream);
		return new DeviceEvent(id, payload);
	}
}
