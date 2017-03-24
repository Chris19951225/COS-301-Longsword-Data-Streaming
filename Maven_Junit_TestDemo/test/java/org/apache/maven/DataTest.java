package org.apache.maven;
import junit.framework.Assert;
import org.junit.Test;

public class DataTest
{
    @Test
    public void testData() {

        Data pack = new Data();

        //01:B1:D6:19:f8:33
        Coordinates controlCoordinates1 = new Coordinates();
        controlCoordinates1.setLatitude(-25.755536);
        controlCoordinates1.setLongitude(28.232402);
        controlCoordinates1.setAltitude(1393.0);

        //00:A0:C9:14:C8:29
        Coordinates controlCoordinates2 = new Coordinates();
        controlCoordinates2.setLatitude(-25.755988);
        controlCoordinates2.setLongitude(28.233234);
        controlCoordinates2.setAltitude(1373.0);

        //00:D0:L9:74:V8:21
        Coordinates controlCoordinates3 = new Coordinates();
        controlCoordinates3.setLatitude(-25.755808);
        controlCoordinates3.setLongitude(28.232251);
        controlCoordinates3.setAltitude(1383.0);

        //Infinity Coordinates
        Coordinates controlCoordinates4 = new Coordinates();
        controlCoordinates4.setLatitude(Double.POSITIVE_INFINITY);
        controlCoordinates4.setLongitude(Double.POSITIVE_INFINITY);
        controlCoordinates4.setAltitude(Double.POSITIVE_INFINITY);

        //Valid
        Assert.assertEquals(controlCoordinates1.getCoordinates(), pack.getLocation("01:B1:D6:19:f8:33").getCoordinates());
        //Invalid
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation("01:B1:D6:19:").getCoordinates());
        //Valid
        Assert.assertEquals(controlCoordinates3.getCoordinates(), pack.getLocation("00:D0:L9:74:V8:21").getCoordinates());
        //Valid
        Assert.assertEquals(controlCoordinates2.getCoordinates(), pack.getLocation("00:A0:C9:14:C8:29").getCoordinates());
        //Invalid
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation("00:A0:C9:14:C8:92").getCoordinates());
        //Invalid
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation(".").getCoordinates());
        //Invalid
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation("!@#$%^&*()_+{}|:<>?").getCoordinates());
        //Invalid
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation("").getCoordinates());
        //Invalid
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation("59875625596sydgfsad").getCoordinates());
        //Valid
        Assert.assertEquals(controlCoordinates3.getCoordinates(), pack.getLocation("00:D0:L9:74:V8:21").getCoordinates());
        //Valid
        Assert.assertEquals(controlCoordinates1.getCoordinates(), pack.getLocation("01:B1:D6:19:f8:33").getCoordinates());
        //Invalid
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation("01B1D619f833").getCoordinates());
        //Valid 
        Assert.assertEquals(controlCoordinates4.getCoordinates(), pack.getLocation("02:B1:D6:19:f8:43").getCoordinates());


    }
    
}
