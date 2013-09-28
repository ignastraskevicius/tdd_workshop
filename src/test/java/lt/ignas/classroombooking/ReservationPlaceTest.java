package lt.ignas.classroombooking;

import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/28/13
 * Time: 9:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReservationPlaceTest {

    //should be no classrooms to list initially
    @Test
    public void shouldBeNoClassroomsToListInitially() {
        ReservationPlace sut = new ReservationPlace();
        assertEquals(sut.getAllClassroomsIds(), Collections.<Integer>emptyList());
    }
}
