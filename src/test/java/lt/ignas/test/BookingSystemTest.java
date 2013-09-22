package lt.ignas.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

    @DataProvider
    public static final Object[][] getHour() {
        return new Object[][] {
            {0},
            {15},
            {23}
        };
    }

    // more cases
    // should book hour
    @Test(dataProvider = "getHour", dependsOnMethods = "shouldReturnNoBookedHoursWhenNobodyBookedYet")
    public void shouldBookOneHour(int hour) {
        booker.book(hour);
        assertEquals(booker.getBookedHours(), asList(hour));
    }

    //should book more hours
    @Test(dependsOnMethods = "shouldBookOneHour")
    public void shouldBookMoreThanOneHour() {
        booker.book(5);
        booker.book(6);
        assertEquals(booker.getBookedHours(), asList(5, 6));
    }

    @DataProvider
    public static final Object[][] getInvalidHours() {
        return new Object[][] {
                {-1},
            {-5},
            {24},
                {105}
        };
    }

    //multiple cases for invalid hour
    // booking should not work for "negative" hour
    @Test(dataProvider = "getInvalidHours", expectedExceptions = IllegalArgumentException.class)
    public void bookingShouldNotWorkForInvalidHour(int invalidHour) {
        booker.book(invalidHour);
    }

    //should throw ISE when hour is already booked
    @Test(expectedExceptions = IllegalStateException.class)
    public void shouldThrowISEWhenBookingHourWhichIsAlreadyBooked() {
        booker.book(5);
        booker.book(5);
    }

}
