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

    Weekday VALID_WEEKDAY = Weekday.MONDAY;
    int VALID_CLASSROOM_ID = 4;

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
    public void shouldSetMoreThanOneBookableClassrooms() {
        Classroom c1 = mock(Classroom.class);
        when(c1.getId()).thenReturn(2);
        Classroom c2 = mock(Classroom.class);
        when(c2.getId()).thenReturn(4);
        ReservationPlace sut = new ReservationPlace(asList(c1, c2));
        assertEquals(sut.getAllClassroomsIds(), asList(2,4));
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
        sut.book(id, Weekday.MONDAY);
        assertFalse(sut.getAvailableClassroomsIds(Weekday.MONDAY).contains(id));
    }

    // not booked classrom should be available
    @Test
    public void notBookedClasseoomShouldBeAvailable() {
        sut.book(4, Weekday.MONDAY);
        assertTrue(sut.getAvailableClassroomsIds(Weekday.MONDAY).contains(2));
    }


    @DataProvider
    public static final Object[][] getWeekday() {
        return new Object[][] {
            {Weekday.MONDAY},
            {Weekday.TUESDAY},
            {Weekday.WEDNESDAY},
            {Weekday.THURSDAY},
            {Weekday.FRIDAY},
            {Weekday.SATURDAY},
            {Weekday.SUNDAY}
        };
    }

    // all cases
    // for monday all classrooms should be available initially
    @Test (dataProvider = "getWeekday")
    public void forAllWeekdaysAllClassroomsShouldBeAvailableInitially(Weekday weekday) {
        assertEquals(sut.getAvailableClassroomsIds(weekday), asList(2, 4));
    }

    // booking a classroom should not remove classroom totally
    @Test
    public void bookingClassromShouldNotRemoveFromAllClassroomList() {
        sut.book(4, Weekday.MONDAY);
        assertEquals(sut.getAllClassroomsIds(), asList(2,4));
    }

    @DataProvider
    public static final Object[][] getWeekdayPairs() {
        return new Object[][] {
            {Weekday.MONDAY, Weekday.TUESDAY},
            {Weekday.TUESDAY, Weekday.MONDAY},
            {Weekday.WEDNESDAY, Weekday.SATURDAY},
            {Weekday.SATURDAY, Weekday.WEDNESDAY}
        };
    }

    // more cases
    // classroom for tuesday should remain available when booking for monday
    @Test  (dataProvider = "getWeekdayPairs")
    public void bookingForSeparateDayShouldRemainClassroomAvailable(Weekday weekdayToBook, Weekday weekdayToVerify) {
        sut.book(4, weekdayToBook);
        assertEquals(sut.getAvailableClassroomsIds(weekdayToVerify), asList(2,4));
    }

    // should book more than one classroom for one day
    @Test
    public void shouldBookMoreThanOneClassroomForOneDay() {
        sut.book(4, VALID_WEEKDAY);
        sut.book(2, VALID_WEEKDAY);
        assertEquals(sut.getAvailableClassroomsIds(VALID_WEEKDAY), Collections.emptyList());
    }

    // should book one room for more than one day
    @Test
    public void shouldBookOneRoomForMoreThanOneDay() {
        sut.book(VALID_CLASSROOM_ID, Weekday.MONDAY);
        sut.book(VALID_CLASSROOM_ID, Weekday.TUESDAY);
        assertFalse(sut.getAvailableClassroomsIds(Weekday.MONDAY).contains(VALID_CLASSROOM_ID));
        assertFalse(sut.getAvailableClassroomsIds(Weekday.TUESDAY).contains(VALID_CLASSROOM_ID));
    }


}
