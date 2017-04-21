package Flink;

// Other imports
import java.net.URL;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.NoHttpResponseException;
import org.apache.http.entity.mime.content.ContentBody;
import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.dom.ElementTraversal;
import org.w3c.*;
import org.apache.flink.api.common.functions.MapFunction;

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

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SocketClientSink;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONArray;

// Gargoyle imports
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@SuppressWarnings("unused")
public class Data
{
    @SuppressWarnings("resource")
	public static void main(String[] args) throws Exception
    {
        // Receiving the address passed through via the Access Interface
        String address = argc[0];
        
        // Logging in to Aruba
        BasicConfigurator.configure();
      
        WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);  //bypass certificate validation
        HtmlPage page1 = webClient.getPage("https://137.215.6.208");
        HtmlForm form = page1.getHtmlElementById("login-form");
        HtmlSubmitInput submitButton = form.getInputByValue("Log In");
        HtmlTextInput textFieldEmail = form.getInputByName("j_username");
        textFieldEmail.setValueAttribute("admin");
        HtmlPasswordInput textFieldPass = form.getInputByName("j_password");
        textFieldPass.setValueAttribute("Aruba123!");
        HtmlPage dashboard = submitButton.click();

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

        DataStream<String> locationRequests = text.map(new MapFunction<String, String>() {
            public String map(String value)
            {
                // Creating request to query aruba for location
                WebResponse response = null;
                URL url=null;
                WebRequest locationReq =null;
                
                try
                {
                	if(url==null)
                		url= new URL("https://137.215.6.208/api/v1/location?sta_eth_mac=" + value);
                    if(locationReq==null)
                    	locationReq = new WebRequest(url);
                    if(response==null)
                    	response =  webClient.loadWebResponse(locationReq);
                }
                catch(Exception e)
                {
                    System.out.println("An exception occurred while creating a URL request object.");
                }

                // Sanitise the data received from Aruba
                org.json.JSONObject obj = null;
                if(response != null)
                {
                    obj = filterArubaReturn(response.getContentAsString());
                }
                else
                {
                    System.out.println("Error. No response received.");
                }

                // Return the sanitised JSON data as a string that can be parsed upon being received
                return (obj.toString() + "\n");
            }
        });


        // Add data sink to return sanitised data through a port
       locationRequests.addSink(new SocketClientSink<String>("localhost",outputPort, new SimpleStringSchema(),-1));

       // Triggers the program execution. The environment will execute all parts of the program that have resulted in a "sink" operation.
       env.execute("Data");
       webClient.close();
       
    }


    // ------------------------------------------------------------------------

    public static org.json.JSONObject filterArubaReturn(String json)
    {
        //Just a tester json object, real one should be passed as the string parameter

        org.json.JSONObject unclean = null;
        org.json.JSONObject cleaned = null;

        try
        {
            unclean = new org.json.JSONObject(json);

            //the bellow can later be removed
            String addr = null;
            double sta_location_x = 0, sta_location_y = 0;
            int error_level = 0;
            boolean associated = false;
            String campus_id = null, building_id = null, floor_id = null, hashed_sta_eth_mac = null;
            String loc_algorithm = null;
            double longitude = 0, latitude = 0, altitude = 0;
            String unit = null;

            JSONArray inner = unclean.getJSONArray("Location_result");
            org.json.JSONObject pos = inner.getJSONObject(0);
            org.json.JSONObject location = pos.getJSONObject("msg");
            org.json.JSONObject address = location.getJSONObject("sta_eth_mac");


            //Things integration doesn't want can be removed later
            addr = address.getString("addr");
            sta_location_x = location.getDouble("sta_location_x");
            sta_location_y = location.getDouble("sta_location_y");
            error_level = location.getInt("error_level");
            associated = location.getBoolean("associated");
            campus_id = location.getString("campus_id");
            floor_id = location.getString("floor_id");
            hashed_sta_eth_mac = location.getString("hashed_sta_eth_mac");
            loc_algorithm = location.getString("loc_algorithm");
            longitude = location.getDouble("longitude");
            latitude =location.getDouble("latitude");
            altitude = location.getDouble("altitude");
            unit = location.getString("unit");

            String clean = "";
            clean += "{\n   \"Location\": {\n";
            clean += "      \"addr\": \"" + addr + "\",\n";
            clean += "      \"sta_location_x\": " + sta_location_x + ",\n";
            clean += "      \"sta_location_y\": " + sta_location_y + ",\n";
            clean += "      \"error_level\": " + error_level + ",\n";
            clean += "      \"associated\": " + associated + ",\n";
            clean += "      \"campus_id\": \"" + campus_id + "\",\n";
            clean += "      \"building_id\": \"" + building_id + "\",\n";
            clean += "      \"floor_id\": \"" + floor_id + "\",\n";
            clean += "      \"hashed_sta_eth_mac\": \"" + hashed_sta_eth_mac + "\",\n";
            clean += "      \"loc_algorithm\": \"" + loc_algorithm + "\",\n";
            clean += "      \"longitude\": " + longitude + ",\n";
            clean += "      \"latitude\": " + latitude + ",\n";
            clean += "      \"altitude\": " + altitude + ",\n";
            clean += "      \"unit\": \"" + unit + "\"\n";
            clean += "  }\n}";

            System.out.println(clean);

            cleaned = new org.json.JSONObject(clean);
        }
        catch(Exception e)
        {
            System.out.println("Exception occurred while filtering JSON object.");
        }

        return cleaned;
    }
}
