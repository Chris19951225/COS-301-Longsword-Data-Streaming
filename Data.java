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

import org.json.JSONObject;
 
public class Data 
{
    public JSONObject getLocation(String add) throws FileNotFoundException, IOException
    {
        File f = new File("C:\\Location.json");
        if (f.exists())
        {
            InputStream is = new FileInputStream("C:\\Location.json");
            String jsonTxt = IOUtils.toString(is);
            JSONObject json = new JSONObject(jsonTxt);       
            
            if(json != null)
            {
                return json; 
            }
        }
        
        return null; 
    }
}
