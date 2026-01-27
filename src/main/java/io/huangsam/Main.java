/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.huangsam;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.TypeDescriptors;

/**
 * A simple hello-world example for Apache Beam.
 *
 * <p>This pipeline creates a PCollection from a list of words, applies a simple transform
 * to prefix each word with "Greeting: ", and writes the results to a text file.
 *
 * <p>Concepts:
 * <pre>
 *   1. Creating a Pipeline
 *   2. Creating a PCollection from in-memory data
 *   3. Applying a simple transform (MapElements)
 *   4. Writing output to a file
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        // Create pipeline options
        PipelineOptions options = PipelineOptionsFactory.create();

        // Create the pipeline
        Pipeline p = Pipeline.create(options);

        // Create a PCollection from a list of strings
        p.apply(Create.of("Hello", "Beam", "World", "Apache", "Data", "Processing"))
                // Apply a transform to prefix each word
                .apply(MapElements.into(TypeDescriptors.strings()).via((String word) -> "Greeting: " + word))
                // Write the results to a file
                .apply(TextIO.write().to("greetings"));

        // Run the pipeline
        p.run().waitUntilFinish();
    }
}
