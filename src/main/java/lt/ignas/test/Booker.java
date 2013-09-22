package lt.ignas.test;

import java.util.ArrayList;
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
    private List<Integer> bookedHours = new ArrayList<Integer>();

    public List<Integer> getBookedHours() {
        return bookedHours;
    }

    public void book(int bookedHour) {
        if(bookedHour < 0 || bookedHour > 23) {
            throw new IllegalArgumentException("Invalid Hour");
        }
        if(bookedHours.contains(bookedHour)) {
            throw new IllegalStateException();
        }
        this.bookedHours.add(bookedHour);
    }
}
