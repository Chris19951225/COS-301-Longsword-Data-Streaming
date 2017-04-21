public class Tester
{
    
    public static void main(String [] args) {
        Data pack = new Data(); 
        
        Coordinates test = pack.getLocation("01:B1:D6:19:f8:33");
        System.out.println(test.getLatitude());
        System.out.println(test.getLongitude());
        System.out.println(test.getAltitude());
    }
    
}
