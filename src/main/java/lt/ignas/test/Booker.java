package lt.ignas.test;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/22/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Booker {
    private Set<Integer> bookedHours = new HashSet<Integer>();

    public List<Integer> getBookedHours() {
        return new ArrayList<Integer>(bookedHours);
    }

    public void book(int bookedHour) {
        if(bookedHour < 0 || bookedHour > 23) {
            throw new IllegalArgumentException("Invalid Hour");
        }
        boolean isBookingSuccessful = this.bookedHours.add(bookedHour);
        if(!isBookingSuccessful) {
            throw new IllegalStateException();
        }
    }
}
