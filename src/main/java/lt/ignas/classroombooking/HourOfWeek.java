package lt.ignas.classroombooking;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/30/13
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class HourOfWeek implements TimeProvider {

    public List<HourOfWeek> values() {
         return null;
    }

    public enum Weekday {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    private Weekday weekday;
    private Hour hour;

    public static HourOfWeek get(Weekday weekday, Hour hour) {
        HourOfWeek time = new HourOfWeek();
        time.weekday = weekday;
        time.hour = hour;
        return time;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public Hour getHour() {
        return hour;
    }
}
