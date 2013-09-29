package lt.ignas.classroombooking;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ReservationPlaceTest {

    Classroom c1;
    Classroom c2;
    ReservationPlace sut;

    @BeforeMethod
    public void setUp() {

        c1 = mock(Classroom.class);
        when(c1.getId()).thenReturn(2);
        c2 = mock(Classroom.class);
        when(c2.getId()).thenReturn(4);
        sut = new ReservationPlace(new ArrayList(asList(c1, c2)));
    }

    //should be no classrooms to list initially
    @Test
    public void shouldBeNoBookableClassroomsInitially() {
        ReservationPlace sut = new ReservationPlace(new ArrayList<Classroom>());
        assertEquals(sut.getAllClassroomsIds(Weekday.MONDAY), Collections.<Integer>emptyList());
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
        assertEquals(sut.getAllClassroomsIds(Weekday.MONDAY), asList(id));
    }

    //shoud set more than one bookable classrooms in constructor
    @Test
    public void shouldSetMoreThanOneBookableClassrooms() {
        Classroom c1 = mock(Classroom.class);
        when(c1.getId()).thenReturn(2);
        Classroom c2 = mock(Classroom.class);
        when(c2.getId()).thenReturn(4);
        ReservationPlace sut = new ReservationPlace(asList(c1, c2));
        assertEquals(sut.getAllClassroomsIds(Weekday.MONDAY), asList(2,4));
    }

    @DataProvider
    public static final Object[][] getBookedId() {
        return new Object[][] {
            {2},
            {4}
        };
    }



    // more cases
    // booked classroom should be unavailable
    @Test (dataProvider = "getBookedId")
    public void bookedClassroomShouldNotBeAvailable(int id) {
        sut.book(id);
        assertFalse(sut.getAvailableClassroomsIds(Weekday.MONDAY).contains(id));
    }

    // not booked classrom should be available
    @Test
    public void notBookedClasseoomShouldBeAvailable() {
        sut.book(4);
        assertTrue(sut.getAvailableClassroomsIds(Weekday.MONDAY).contains(2));
    }

    // for monday all classrooms should be available initially
    @Test
    public void forMondayAllClassroomsShouldBeAvailable() {
        assertEquals(sut.getAvailableClassroomsIds(Weekday.MONDAY), asList(2, 4));
    }

    // all rooms should remain bookable after booking one

}
