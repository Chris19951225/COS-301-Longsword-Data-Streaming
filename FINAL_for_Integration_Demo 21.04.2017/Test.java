package org.apache.flink;


public class Test {

    public static void main(String[] args) throws Exception {
        //Creates a Data2 object and passes a mac address through then prints the output
        Data2 data2obj= new Data2();
        System.out.println("beginning test");
        org.json.JSONObject obj = data2obj.getLocation("48:5A:3F:79:A3:D1");
        System.out.println("print location");
        System.out.println(obj);
    }
}
