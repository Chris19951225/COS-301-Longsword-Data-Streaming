/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hristian Vitrychenko
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
public class Filter //Only an example of filter, actual implementation will be different 
{
    public static void main(String[] args) throws IOException
    {
        //testing to see if correct json object made
        System.out.println(filterArubaReturn("Testing")); 
    }
    
    public static JSONObject filterArubaReturn(String json) throws FileNotFoundException, IOException
    {
        //Just a tester json object, real one should be passed as the string parameter
        
        JSONObject unclean = new JSONObject(json);
        
        //the bellow can later be removed 
        String addr = null;
        double sta_location_x = 0, sta_location_y = 0;
        int error_level = 0; 
        boolean associated = false;
        String campus_id = null, building_id = null, floor_id = null, hashed_sta_eth_mac = null; 
        String loc_algorithm = null;
        double longitude = 0, latitude = 0, altitude = 0;
        
        JSONArray inner = unclean.getJSONArray("Location_result"); 
        JSONObject pos = inner.getJSONObject(0);
        JSONObject location = pos.getJSONObject("msg"); 
        JSONObject address = location.getJSONObject("sta_eth_mac"); 
        
        
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
        clean += "  }\n}"; 
        
        JSONObject cleaned = new JSONObject(clean);
        
        return cleaned; 
    }
}
