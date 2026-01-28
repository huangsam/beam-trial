package io.huangsam;

import io.huangsam.functions.DeviceEventGenerator;
import io.huangsam.functions.SensorEventFilter;
import io.huangsam.functions.EventProcessor;
import io.huangsam.functions.DeviceStatsBuilder;
import io.huangsam.model.DeviceEvent;
import io.huangsam.model.DeviceEventCoder;
import io.huangsam.model.DeviceStats;
import io.huangsam.model.DeviceStatsCoder;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.joda.time.Duration;

/**
 * A Beam pipeline demonstrating streaming-like analytics similar to Flink.
 *
 * <p>
 * This pipeline simulates device event processing with filtering, side outputs,
 * and aggregation, showcasing Beam's capabilities for complex data processing.
 */
public class Main {

	public static void main(String[] args) {
		// Create pipeline options
		PipelineOptions options = PipelineOptionsFactory.create();

		// Create the pipeline
		Pipeline p = Pipeline.create(options);

		// Generate sequence of numbers (simulating streaming input)
		java.util.List<Long> sequenceList = new java.util.ArrayList<>();
		for (long i = 1; i <= 500; i++) {
			sequenceList.add(i);
		}
		PCollection<Long> sequences = p.apply(Create.of(sequenceList));

		// Generate device events from sequences
		PCollection<DeviceEvent> rawEvents = sequences
				.apply("Generate Device Events", ParDo.of(new DeviceEventGenerator())).setCoder(DeviceEventCoder.of());

		// Filter to keep only sensor events
		PCollection<DeviceEvent> sensorEvents = rawEvents.apply("Filter Sensor Events",
				Filter.by(new SensorEventFilter()));

		// Process events with side output for errors
		TupleTag<DeviceEvent> mainTag = new TupleTag<>("main-output");
		PCollectionTuple results = sensorEvents.apply("Process Events",
				ParDo.of(new EventProcessor()).withOutputTags(mainTag, TupleTagList.of(EventProcessor.ERROR_EVENTS)));

		// Get main output and error events from side output
		PCollection<DeviceEvent> processedEvents = results.get(mainTag).setCoder(DeviceEventCoder.of());
		PCollection<DeviceEvent> errorEvents = results.get(EventProcessor.ERROR_EVENTS).setCoder(DeviceEventCoder.of());

		// Create device statistics by counting events per device in 5-second windows
		PCollection<DeviceStats> statsCollection = processedEvents.apply("Map to KV",
				MapElements.into(TypeDescriptors.kvs(TypeDescriptors.strings(), TypeDescriptors.longs()))
						.via((DeviceEvent event) -> {
							assert event != null;
							return KV.of(event.id(), 1L);
						}))
				// Apply 5-second fixed window (similar to Flink's
				// TumblingProcessingTimeWindows)
				.apply("5s Window", Window.into(FixedWindows.of(Duration.standardSeconds(5))))
				.apply("Count per Device", Count.perKey())
				.apply("Build Device Stats", ParDo.of(new DeviceStatsBuilder())).setCoder(DeviceStatsCoder.of());

		PCollection<String> deviceStats = statsCollection.apply("Format Analytics",
				MapElements.into(TypeDescriptors.strings()).via((DeviceStats stats) -> {
					assert stats != null;
					return String.format("ANALYTICS [%s]: %d events in window [%d, %d]", stats.deviceId(),
							stats.eventCount(), stats.windowStart(), stats.windowEnd());
				}));

		// Format outputs
		PCollection<String> processedOutput = processedEvents.apply("Format Processed",
				MapElements.into(TypeDescriptors.strings()).via((DeviceEvent event) -> {
					assert event != null;
					return "PROCESSED: " + event.id() + " -> " + event.payload().toUpperCase();
				}));

		PCollection<String> errorOutput = errorEvents.apply("Format Errors",
				MapElements.into(TypeDescriptors.strings()).via((DeviceEvent event) -> {
					assert event != null;
					return "ERROR EVENT: " + event.id() + " - " + event.payload();
				}));

		// Write outputs to files
		deviceStats.apply("Write Analytics", TextIO.write().to("analytics").withSuffix(".txt"));
		processedOutput.apply("Write Processed", TextIO.write().to("processed").withSuffix(".txt"));
		errorOutput.apply("Write Errors", TextIO.write().to("errors").withSuffix(".txt"));

		// Run the pipeline
		p.run().waitUntilFinish();
	}
}
