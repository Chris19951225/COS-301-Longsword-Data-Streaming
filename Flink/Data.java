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

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SocketClientSink;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;
import org.apache.sling.commons.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Implements a streaming windowed version of the "WordCount" program.
 *
 * This program connects to a server socket and reads strings from the socket.
 * The easiest way to try this out is to open a text server (at port 12345)
 * using the <i>netcat</i> tool via
 * <pre>
 * nc -l 12345
 * </pre>
 * and run this example with the hostname and the port as arguments.
 */
@SuppressWarnings("serial")
public class Data {



    public static void main(String[] args) throws Exception {

        // the host and the port to connect to
        final String hostname = "localhost";
        final int port = 9000;
        final int outputPort = 9002;

        // get the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // get input data by connecting to the socket
        DataStream<String> text = env.socketTextStream(hostname, port, "\n");

        // parse the data, group it, window it, and aggregate the counts
        DataStream<String> windowCounts = text

        .flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) {
               try
               {
                   JSONObject obj = getLocation();
                   String str = obj.toString();
                   out.collect("MAC-Address: " + value + " with location: " +str + "\n");
               }
               catch(Exception e)
               {
                    System.out.println("Error");
               }

            }
        });

        // Add data sink to return sanitised data
        windowCounts.addSink(new SocketClientSink<String>("localhost",outputPort, new SimpleStringSchema()));

        env.execute("Socket Window WordCount");
    }

    // ------------------------------------------------------------------------

    /**
     * Data type for words with count
     */

    public static JSONObject getLocation() throws FileNotFoundException, IOException {

        JSONObject obj = null;

        try {
            obj = new JSONObject("{\n" +
                    "  \"Location\": {\n" +
                    "    \"Latitude\" : \"-25.755988\",\n" +
                    "    \"Longitude\": \"28.233234\",\n" +
                    "    \"Altitude\" :  \"10m\"\n" +
                    "  }\n" +
                    "}");

        } catch (Exception e) {

            e.printStackTrace();
        }


        return obj;

    }
}

