
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

public class ClientLoginWeb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
      
      BasicConfigurator.configure();
      WebClient webClient = new WebClient();
      HtmlPage page1 = webClient.getPage("https://137.215.6.208");
      HtmlForm form = page1.getHtmlElementById("login-form");
      
      HtmlSubmitInput submitButton = form.getInputByValue("Log In");
      HtmlTextInput textFieldEmail = form.getInputByName("j_username");
      textFieldEmail.setValueAttribute("admin");
      HtmlPasswordInput textFieldPass = form.getInputByName("j_password");
      textFieldPass.setValueAttribute("Aruba123!");
      HtmlPage page2 = submitButton.click();
      
      System.out.println(page2);
    }
}
