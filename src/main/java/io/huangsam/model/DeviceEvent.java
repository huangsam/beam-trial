package io.huangsam.model;

import java.io.Serializable;

/**
 * Represents a device event with an ID and payload.
 */
public record DeviceEvent(String id, String payload) implements Serializable {
}
