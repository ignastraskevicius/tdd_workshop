package lt.ignas.classroombooking;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/30/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class HourOfWeekTest {

    //should get HourOfWeek By Weekday
    @Test
    public void shouldGetHourOfWeek() {
        HourOfWeek time = HourOfWeek.get(Weekday.MONDAY, Hour.AM_8);
        Assert.assertEquals(time.getWeekday(), Weekday.MONDAY);
        Assert.assertEquals(time.getHour(), Hour.AM_8);
    }




}
