package org.apache.flink;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Flink imports
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SocketClientSink;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

public class Data
{
    public static void main(String[] args) throws Exception
    {
        // The host and the port to connect to
        final String hostname = "localhost";
        final int port = 9000;
        final int outputPort = 9004;

        // Get the Flink execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Sets the parallelism for operations executed through this environment. Setting a parallelism of x here will cause all operators (such as join, map, reduce) to run with x parallel instances.
        env.setParallelism(4);

        // Create a data stream to receive text via a socket
        DataStream<String> text = env.socketTextStream(hostname, port, "\n");


        //
        DataStream<String> locationRequests = text
            .map(new MapFunction<String, String>() {
            public String map(String value)
            {
                // Return the sanitised JSON data as a string that can be parsed upon being received
                return (value + "\n");
            }
        });


        // Add data sink to return sanitised data through a port
       locationRequests.addSink(new SocketClientSink<String>("localhost",outputPort, new SimpleStringSchema(),-1));

       // Triggers the program execution. The environment will execute all parts of the program that have resulted in a "sink" operation.
       env.execute("Data");
    }

    // ------------------------------------------------------------------------
}

