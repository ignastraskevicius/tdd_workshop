package lt.ignas.classroombooking;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/30/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class HourOfWeekTest {



   @DataProvider
    public static final Object[][] getTimes() {
        return new Object[][] {
            {HourOfWeek.Weekday.MONDAY, Hour.AM_8},
            {HourOfWeek.Weekday.MONDAY, Hour.PM_6},
            {HourOfWeek.Weekday.SUNDAY, Hour.AM_8}

        };
    }

    //more cases , more
    //should get HourOfWeek By Weekday
    @Test  (dataProvider = "getTimes")
    public void shouldGetHourOfWeek(HourOfWeek.Weekday weekday, Hour hour) {
        HourOfWeek time = HourOfWeek.get(weekday, hour);
        Assert.assertEquals(time.getWeekday(), weekday);
        Assert.assertEquals(time.getHour(), hour);
    }

    //


}
