package lt.ignas.classroombooking;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
* Created with IntelliJ IDEA.
* User: ignas
* Date: 10/1/13
* Time: 9:03 AM
* To change this template use File | Settings | File Templates.
*/

@Test
public class StaticTest {

    @DataProvider
    public final Object[][] getWeekday() {
        return new Object[][] {
                {new CustomClass()}
        };
    }

    @Test (dataProvider = "getWeekday")
    public void forAllWeekdaysAllClassroomsShouldBeAvailableInitially(Object p) {
        assertNotNull(p);
    }

}

class CustomClass {}
