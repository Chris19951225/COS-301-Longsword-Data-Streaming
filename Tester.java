/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hristian Vitrychenko
 */

import java.io.IOException;

import org.json.JSONObject;
public class Tester 
{
    
    public static void main(String [] args) throws IOException
    {
        Data pack = new Data(); 
        
        JSONObject test = pack.getLocation("Shit"); 
        System.out.println(test); 
    }
    
}
