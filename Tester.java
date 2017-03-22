import java.io.IOException;
import org.json.simple.JSONObject;

public class Tester 
{
    
    public static void main(String [] args) throws IOException
    {
        Data pack = new Data(); 
        
        JSONObject test = pack.getLocation("00:A0:C9:14:C8:29");
        System.out.println(test); 
    }
    
}
