/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hristian Vitrychenko
 */
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
public class Filter 
{
    public JSONObject filterArubaReturn(String json)
    {
        JSONObject unclean = new JSONObject(json);
        
        //the bellow can later be removed 
        String sta_location_x = null, sta_location_y = null, error_level = null; 
        String associated = null, campus_id = null, building_id = null, floor_id = null, hashed_sta_eth_mac = null; 
        String loc_algorithm = null, longitude = null, latitude = null, altitude = null, unit = null; 
        
        JSONObject inner = unclean.getJSONObject("Location_result"); 
        JSONObject location = inner.getJSONObject("msg");
        
        
        //Things integration doesn't want can be removed later 
        sta_location_x = location.getString("sta_location_x");
        sta_location_y = location.getString("sta_location_y");
        error_level = location.getString("error_level");
        associated = location.getString("associated");
        campus_id = location.getString("campus_id");
        floor_id = location.getString("floor_id");
        hashed_sta_eth_mac = location.getString("hashed_sta_eth_mac");
        loc_algorithm = location.getString("loc_algorithm");
        longitude = location.getString("longitude");
        latitude =location.getString("latitude");
        altitude = location.getString("altitude");
        unit = location.getString("unit");
        
        String clean = "";
        clean += "{'Location': {";
        clean += "'sta_location_x': "+ sta_location_x + ",";
        clean += "'sta_location_y': " + sta_location_y + ","; 
        clean += "'error_level': " + error_level + ","; 
        clean += "'associated': " + associated + ",";
        clean += "'campus_id': " + campus_id + ",";
        clean += "'building_id': " + building_id + ","; 
        clean += "'floor_id': " + floor_id + ",";
        clean += "'hashed_sta_eth_mac': " + hashed_sta_eth_mac + ",";
        clean += "'loc_algorithm': " + loc_algorithm + ",";
        clean += "'longitude': " + longitude + ",";
        clean += "'latitude': " + latitude + ",";
        clean += "'altitude': " + altitude + ",";
        clean += "'unit': " + unit;
        clean += "}}"; 
        
        JSONObject cleaned = new JSONObject(clean);
        
        return cleaned; 
    }
}
