# Apache Beam Hello World Guide

This project contains a simple "Hello World" example for Apache Beam, demonstrating the basic concepts of creating and running a pipeline.

## Overview

The `Main` class creates a simple Apache Beam pipeline that:
1. Creates a PCollection from a list of words: "Hello", "Beam", "World"
2. Applies a transform to prefix each word with "Greeting: "
3. Writes the results to text files starting with "greetings"

## Prerequisites

- Java 11 or higher
- Gradle (wrapper included)

## Running the Example

1. **Build the project:**
   ```bash
   ./gradlew build
   ```

2. **Run the pipeline:**
   ```bash
   ./gradlew run
   ```

   Alternatively, you can run it directly with Java:
   ```bash
   java -cp "$(./gradlew -q printClasspath):build/classes/java/main" io.huangsam.Main
   ```

   Note: The `printClasspath` task might need to be added to `build.gradle.kts` if not present.

3. **Check the output:**
   After running, you'll find output files in the project root directory, such as:
   - `greetings-00000-of-00003`
   - `greetings-00001-of-00003`
   - `greetings-00002-of-00003`

   (The exact number of files may vary; Beam shards the output for parallel processing.)

   Each file contains lines like:
   ```
   Greeting: Hello
   Greeting: Beam
   Greeting: World
   ```

## Understanding the Code

- **Pipeline Creation:** `Pipeline.create(options)` sets up the execution environment.
- **Data Creation:** `Create.of()` creates a PCollection from in-memory data.
- **Transformation:** `MapElements` applies a function to each element.
- **Output:** `TextIO.write()` saves the results to files.
- **Execution:** `p.run().waitUntilFinish()` executes the pipeline and waits for completion.

## Next Steps

- Explore more transforms in the [Apache Beam documentation](https://beam.apache.org/documentation/).
- Try reading from files using `TextIO.read()`.
- Experiment with different runners (e.g., Dataflow, Flink) for distributed execution.
