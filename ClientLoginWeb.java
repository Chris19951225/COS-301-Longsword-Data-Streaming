/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientloginweb;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.w3c.css.sac.ErrorHandler;
import org.apache.http.NoHttpResponseException;
import org.apache.commons.codec.DecoderException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.http.protocol.ImmutableHttpProcessor;

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
