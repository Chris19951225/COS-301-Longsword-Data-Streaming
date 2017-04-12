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
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SocketClientSink;
import org.apache.flink.streaming.api.operators.ChainingStrategy;
import org.apache.flink.streaming.api.transformations.StreamTransformation;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.util.serialization.SerializationSchema;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;
import org.apache.sling.commons.json.JSONObject;

import javax.net.ssl.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.io.FileReader;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;


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
       // final String hostname = "localhost";
       // final int port = 9000;

        // get the execution environment
       // final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //tmp.writeToSocket("localhost",9000,new SimpleStringSchema());

        // get input data by connecting to the socket
       // DataStream<String> text = env.socketTextStream(hostname, port);


        // parse the data, group it, window it, and aggregate the counts
       // DataStream<String> windowCounts = text

//                .flatMap(new FlatMapFunction<String, String>() {
//                    @Override
//                    public void flatMap(String value, Collector<String> out) {
//                        try
//                        {
                            JSONObject obj = getLocation();
//                            String url = "https://137.215.6.208:443/api/v1/location?sta_eth_mac=48:5A:3F:79:A3:D1";
//                            URL obj = new URL(url);
//                            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//                            con.setRequestMethod("GET");
//                            con.setRequestProperty("User-Agent", "Mozilla/5.0");
//                            int responseCode = con.getResponseCode();
//                            System.out.println("\nSending 'GET' request to URL : " + url);
//                            System.out.println("Response Code : " + responseCode);

//                            String str = obj.toString();
//                            out.collect("MAC-Address: " + value + " with location: " + "\n" +str);
//
//                        }
//                        catch(Exception e)
//                        {
//                            System.out.println("Error");
//                        }
//
//                    }
//                });
//
//        // print to standard out
//
//        windowCounts.print();
//
//        env.execute("Socket Window WordCount");
    }

    // ------------------------------------------------------------------------

    /**
     * Providing getLocation service.
     Returning a JSON object as initially requested from integration.
     */

    public static JSONObject getLocation() throws FileNotFoundException, IOException {

        //Connecting to the ALE server, requesting location for MAC 48:5A:3F:79:A3:D1(Peter's Phone)

        System.out.println("TESTER");
        JSONObject obj = null;
        String url = "https://137.215.6.208";

        String urlTmp = "https://137.215.6.208/api/v1/location?sta_eth_mac=48:5A:3F:79:A3:D1";
        //System.setProperty("axis.socketSecureFactory","org.apache.axis.components.net.SunFakeTrustSocketFactory");

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
            public boolean verify(String string,SSLSession ssls) {
                return true;
            }
        });


        URL objOne = new URL(url);
        URL objTwo = new URL(urlTmp);
        HttpsURLConnection connOne = (HttpsURLConnection) objOne.openConnection();
        connOne.setRequestMethod("GET");
        int responseCode = connOne.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);



        //HttpsURLConnection connTwo = (HttpsURLConnection) objTwo.openConnection();
       // int responseCode2 = connOne.getResponseCode();
       // connOne.setRequestMethod("GET");
        String response = connOne.getResponseMessage();
        //System.out.println("\nSending 'GET' request to URL : " + urlTmp);
       // System.out.println("Response Code : " + responseCode2);
        System.out.println(response);



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



    /**
     * DO NOT USE IN PRODUCTION!!!!
     *
     * This class will simply trust everything that comes along.
     *
     * @author frank
     *
     */
    public static class TrustAllX509TrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) {
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) {
        }

    }


}


