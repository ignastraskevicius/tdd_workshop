package lt.ignas.test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/22/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Booker {
    private Integer bookedHour = null;

    public List<Integer> getBookedHours() {
        List<Integer> bookedHours;
        if (bookedHour == null) {
            bookedHours = Collections.emptyList();
        } else {
            bookedHours = asList(5);
        }
        return bookedHours;
    }

    public void book(int bookedHour) {
        this.bookedHour = bookedHour;
    }
}
