package lt.ignas.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/22/13
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class BookingSystemTest {

    private Booker booker;

    @BeforeMethod
    public void setUp() {
        booker = new Booker();
    }

    // should return no hours when nobady has booked yet
    public void shouldReturnNoBookedHoursWhenNobodyBookedYet() {
        assertEquals(booker.getBookedHours(), Collections.emptyList());
    }

    // should book hour
    public void shouldBookOneHour() {
        booker.book(5);
        assertEquals(booker.getBookedHours(), asList(5));
    }
}
