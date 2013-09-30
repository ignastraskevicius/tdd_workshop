package lt.ignas.classroombooking;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/30/13
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class HourOfWeek {
    private Weekday weekday;
    private Hour hour;

    public static HourOfWeek get(Weekday weekday, Hour hour) {
        HourOfWeek time = new HourOfWeek();
        time.weekday = Weekday.MONDAY;
        time.hour = Hour.AM_8;
        return time;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public Hour getHour() {
        return hour;
    }
}
