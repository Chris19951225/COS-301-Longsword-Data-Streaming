//import org.json.JSONObject;

public class Data implements Data_Interface {
    public String getLocation(String add) {

        String coordinates = "";

        //Lecture hall
        if(add.equals("00:A0:C9:14:C8:29")){
            coordinates = "{ " +
                    "\"Latitude\":\"-25.755988\"," +
                    "\"Longitude\":\"28.233234\", " +
                    "\"Altitude\":\"1373m\"" +
                    " }";
        }
        //Server Lab
        else if (add.equals("01:B1:D6:19:f8:33")){
            coordinates = "{ " +
                    "\"Latitude\":\"-25.755781\", " +
                    "\"Longitude\":\"28.233037\", " +
                    "\"Altitude\":\"1393m\"" +
                    " }";
        }
        //EMB building
        else if (add.equals("00:D0:L9:74:V8:21")){
            coordinates = "{ " +
                    "\"Latitude\":\"-25.755224\", " +
                    "\"Longitude\":\"28.232473a\", " +
                    "\"Altitude\":\"1383m\"" +
                    " }";
        }

        //JSONObject jsonObj = new JSONObject(jsonString.toString());
        return coordinates;

    }
}




