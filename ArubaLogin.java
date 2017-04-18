
package clientloginweb;
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
import javax.xml.ws.Response;

public class ClientLoginWeb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
      
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
      URL url= new URL("https://137.215.6.208/api/v1/location?sta_eth_mac=48:9D:24:B9:41:CB");
      WebRequest locationReq = new WebRequest(url);
      WebResponse response =  webClient.loadWebResponse(locationReq); 
       
      System.out.println("=========================================================" );
      System.out.println( response.getContentAsString());
      System.out.println("=========================================================" );
      webClient.close();  
      
    }
}
