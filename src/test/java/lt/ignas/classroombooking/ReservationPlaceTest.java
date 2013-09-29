package lt.ignas.classroombooking;

import org.testng.annotations.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    //should set bookable classrooms in constructor
    @Test
    public void shouldSetBookableClassroomsInConstructor() {
        Classroom c = mock(Classroom.class);
        when(c.getId()).thenReturn(1);
        ReservationPlace sut = new ReservationPlace(asList(c));
        assertEquals(sut.getAllClassroomsIds(), asList(1));
    }
}
