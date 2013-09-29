package lt.ignas.classroombooking;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
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
        ReservationPlace sut = new ReservationPlace(new ArrayList<Classroom>());
        assertEquals(sut.getAllClassroomsIds(), Collections.<Integer>emptyList());
    }

    @DataProvider
    public static final Object[][] getId() {
        return new Object[][] {
            {1},
            {2},
            {15}
        };
    }


    //different ids
    //should set bookable classroom in constructor
    @Test  (dataProvider = "getId")
    public void shouldSetOneBookableClassroomInConstructor(Integer id) {
        Classroom c = mock(Classroom.class);
        when(c.getId()).thenReturn(id);
        ReservationPlace sut = new ReservationPlace(asList(c));
        assertEquals(sut.getAllClassroomsIds(), asList(id));
    }

    //shoud set more than one bookable classrooms in constructor
    @Test
    public void shouldSetMoreThanOneBOokableClassrooms() {
        Classroom c1 = mock(Classroom.class);
        when(c1.getId()).thenReturn(2);
        Classroom c2 = mock(Classroom.class);
        when(c2.getId()).thenReturn(4);
        ReservationPlace sut = new ReservationPlace(asList(c1, c2));
        assertEquals(sut.getAllClassroomsIds(), asList(2,4));
    }



}
