import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by user on 2017/03/22.
 */
public interface Data_Interface
{
    public JSONObject getLocation(String address) throws FileNotFoundException, IOException;
}
