package org.apache.flink;
    //package clientloginweb;
            import com.gargoylesoftware.htmlunit.WebClient;
            import com.gargoylesoftware.htmlunit.html.HtmlForm;
            import com.gargoylesoftware.htmlunit.html.HtmlPage;
            import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
            import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
            import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
            import com.gargoylesoftware.htmlunit.WebResponse;
            import com.gargoylesoftware.htmlunit.WebRequest;
            import java.net.URL;
            import org.apache.log4j.BasicConfigurator;
            import org.json.JSONArray;
            import org.json.JSONObject;

            import javax.xml.ws.Response;


public class Data2 {

        public org.json.JSONObject getLocation(String macAddr) throws Exception {

            BasicConfigurator.configure();
            WebClient webClient = new WebClient();
            webClient.getOptions().setUseInsecureSSL(true);  //bypass certificate validation
            HtmlPage page1 = webClient.getPage("https://137.215.6.208");
            HtmlForm form = page1.getHtmlElementById("login-form");
            //logging in
            HtmlSubmitInput submitButton = form.getInputByValue("Log In");
            HtmlTextInput textFieldEmail = form.getInputByName("j_username");
            textFieldEmail.setValueAttribute("admin");
            HtmlPasswordInput textFieldPass = form.getInputByName("j_password");
            textFieldPass.setValueAttribute("Aruba123!");
            HtmlPage dashboard = submitButton.click();

            //Creating request to query aruba for location
            URL url = new URL("https://137.215.6.208/api/v1/location?sta_eth_mac=" + macAddr);
            WebRequest locationReq = new WebRequest(url);
            WebResponse response = webClient.loadWebResponse(locationReq);

            System.out.println("=========================================================");
            //System.out.println(response.getContentAsString());
            webClient.close();
            return filterArubaReturn(response.getContentAsString());
            //System.out.println("=========================================================");


        }

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
