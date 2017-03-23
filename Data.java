public class Data implements Data_Interface {
    public Coordinates getLocation(String add) {

        Coordinates coordinates = new Coordinates();

        if(add.equals("00:A0:C9:14:C8:29")){
            coordinates.setLatitude(-25.755988);
            coordinates.setLongitude(28.233234);
            coordinates.setAltitude(1373);
        }
        else if (add.equals("01:B1:D6:19:f8:33")){
            coordinates.setLatitude(-25.755536);
            coordinates.setLongitude(28.232402);
            coordinates.setAltitude(20);
        }
        else if (add.equals("00:D0:L9:74:V8:21")){
            coordinates.setLatitude(-25.755808);
            coordinates.setLongitude(28.232251);
            coordinates.setAltitude(10);
        }

        return coordinates;

    }
}
        



