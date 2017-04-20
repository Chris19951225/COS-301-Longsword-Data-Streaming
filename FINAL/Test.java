package org.apache.flink;


/**
 * Created by Ritz on 2017/04/20.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Data2 data2obj= new Data2();
        System.out.println("beginning test");
        org.json.JSONObject obj = data2obj.getLocation("48:5A:3F:79:A3:D1");
        System.out.println("print location");
        System.out.println(obj);
    }
}
