package lt.agmis.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Test
public class HashMapTest {
     private Map<String, String> sut;

    @BeforeMethod
    public void setUp() {
        sut = new HashMap<String, String>();
    }

    public void shouldPutValue(){
        sut.put("key", "value");
        Assert.assertEquals(sut.get("key"), "value");
    }

    public void putShouldReplaceOldStringValue(){
        String value1 = "value1";
        String value2 = "value2";

        sut.put("key", value1);
        sut.put("key", value2);
        Assert.assertEquals(sut.get("key"), value2);
    }
}
