import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import org.json.simple.JSONObject;


public class Data implements Data_Interface {
    public JSONObject getLocation(String add) throws FileNotFoundException, IOException {

        Object obj = null;

        try {
            JSONParser parser = new JSONParser();
            obj = parser.parse(new FileReader("Location.json"));

        } catch (Exception e) {

            e.printStackTrace();
        }

        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;

    }
}
        



