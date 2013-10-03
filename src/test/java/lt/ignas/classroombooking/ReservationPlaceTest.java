package lt.ignas.classroombooking;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@Test
public class ReservationPlaceTest {

    Classroom classroom1;
    Classroom classroom2;
    final int ID_CLASSROOM_1 = 2;
    final int ID_CLASSROOM_2 = 4;
    int VALID_CLASSROOM_ID = ID_CLASSROOM_2;

    ReservationPlace sut;

    HourOfWeek sunday8AM;
    HourOfWeek sunday6PM;
    HourOfWeek monday8AM;
    HourOfWeek monday6PM;
    HourOfWeek VALID_TIME;

    TimeProvider timeProvider;

    @BeforeClass
    public void setUpConstants() {
        List<HourOfWeek> possibleTimes = new ArrayList<HourOfWeek>();
        sunday8AM = mockTime(possibleTimes, HourOfWeek.Weekday.SUNDAY, Hour.AM_8);
        sunday6PM = mockTime(possibleTimes, HourOfWeek.Weekday.SUNDAY, Hour.PM_6);
        monday8AM = mockTime(possibleTimes, HourOfWeek.Weekday.MONDAY, Hour.AM_8);
        monday6PM = mockTime(possibleTimes, HourOfWeek.Weekday.MONDAY, Hour.PM_6);

        VALID_TIME = sunday8AM;

        timeProvider = mock(TimeProvider.class);
        when(timeProvider.values()).thenReturn(possibleTimes);

        classroom1 = mock(Classroom.class);
        when(classroom1.getId()).thenReturn(ID_CLASSROOM_1);
        classroom2 = mock(Classroom.class);
        when(classroom2.getId()).thenReturn(ID_CLASSROOM_2);
    }

    @BeforeMethod
    public void setUpSutAndDependencies() {
        sut = new ReservationPlace();
        sut.setProvider(timeProvider);
        sut.setBookableClassrooms(new ArrayList(asList(classroom1, classroom2)));
    }

    @Test()
    public void shouldBeNoBookableClassroomsInitially() {
        ReservationPlace sut = new ReservationPlace();
        sut.setProvider(timeProvider);
        assertEquals(sut.getAllClassroomsIds(), Collections.<Integer>emptyList());
    }

    @DataProvider
    public final Object[][] getId() {
        return new Object[][] {
            {ID_CLASSROOM_1},
            {ID_CLASSROOM_2}
        };
    }

    @Test  (dataProvider = "getId")
    public void shouldSetOneBookableClassroom(Integer id) {
        Classroom c = mock(Classroom.class);
        when(c.getId()).thenReturn(id);
        ReservationPlace sut = new ReservationPlace();
        sut.setProvider(timeProvider);
        sut.setBookableClassrooms(asList(c));
        assertEquals(sut.getAllClassroomsIds(), asList(id));
    }

    @Test()
    public void shouldSetMoreThanOneBookableClassrooms() {
        ReservationPlace sut = new ReservationPlace();
        sut.setProvider(timeProvider);
        sut.setBookableClassrooms(asList(classroom1, classroom2));
        assertEquals(sut.getAllClassroomsIds(), asList(ID_CLASSROOM_1, ID_CLASSROOM_2));
    }

    @DataProvider
    public final Object[][] getBookedId() {
        return new Object[][] {
            {ID_CLASSROOM_1},
            {ID_CLASSROOM_2}
        };
    }

    @Test (dataProvider = "getBookedId")
    public void bookedClassroomShouldNotBeAvailable(int id) {
        sut.book(id, VALID_TIME);
        assertFalse(sut.getAvailableClassroomsIds(VALID_TIME).contains(id));
    }

    // another room is booked - also exception
    //should throw ISE when booking classroom which is already booked
    @Test (dataProvider = "getBookedId", expectedExceptions = IllegalStateException.class)
    public void shouldThrowISEWhenBookingClassroomWhichIsAlreadyBooked(int classroomId) {
        sut.book(classroomId, VALID_TIME);
        sut.book(classroomId, VALID_TIME);
    }

    @Test()
    public void notBookedClasseoomShouldBeAvailable() {
        sut.book(ID_CLASSROOM_2, VALID_TIME);
        assertTrue(sut.getAvailableClassroomsIds(VALID_TIME).contains(ID_CLASSROOM_1));
    }


    @DataProvider
    public final Object[][] getWeekday() {
        return new Object[][] {
            {sunday8AM},
            {sunday6PM},
            {monday8AM},
            {monday6PM}
        };
    }

    @Test (dataProvider = "getWeekday")
    public void forAllWeekdaysAllClassroomsShouldBeAvailableInitially(HourOfWeek time) {
        assertEquals(sut.getAvailableClassroomsIds(time), asList(ID_CLASSROOM_1, ID_CLASSROOM_2));
    }

    @Test
    public void bookingClassromShouldNotRemoveFromAllClassroomList() {
        sut.book(ID_CLASSROOM_2, VALID_TIME);
        assertEquals(sut.getAllClassroomsIds(), asList(ID_CLASSROOM_1,ID_CLASSROOM_2));
    }

    @DataProvider
    public Object[][] getWeekdayPairs() {

       return new Object[][] {
            {sunday8AM, sunday6PM},
            {sunday6PM, sunday8AM},
            {sunday6PM, monday8AM},
            {monday8AM, sunday6PM}
        };
    }

    @Test  (dataProvider = "getWeekdayPairs")
    public void bookingForSeparateDayShouldRemainClassroomAvailable(HourOfWeek timeToBook, HourOfWeek timeToVerify) {
        sut.book(ID_CLASSROOM_2, timeToBook);
        assertEquals(sut.getAvailableClassroomsIds(timeToVerify), asList(ID_CLASSROOM_1,ID_CLASSROOM_2));
    }

    @Test
    public void shouldBookMoreThanOneClassroomForOneDay() {
        sut.book(ID_CLASSROOM_2, VALID_TIME);
        sut.book(ID_CLASSROOM_1, VALID_TIME);
        assertEquals(sut.getAvailableClassroomsIds(VALID_TIME), Collections.emptyList());
    }

    @Test
    public void shouldBookOneRoomForTwoOrMoreSeparateDay() {
        sut.book(VALID_CLASSROOM_ID, sunday8AM);
        sut.book(VALID_CLASSROOM_ID, sunday6PM);
        assertFalse(sut.getAvailableClassroomsIds(sunday8AM).contains(VALID_CLASSROOM_ID));
        assertFalse(sut.getAvailableClassroomsIds(sunday6PM).contains(VALID_CLASSROOM_ID));
    }



    //should throw ISE when booking classroom with size larger than lasgest
    @Test    (expectedExceptions = IllegalStateException.class)
    public void shouldThrowISEWhenBookingClassroomWithSizeLargerThanLargest() {
        when(classroom1.getSize()).thenReturn(10);
        when(classroom2.getSize()).thenReturn(10);
        Criteria criteria = mock(Criteria.class);
        when(criteria.getTime()).thenReturn(sunday6PM);
        when(criteria.getSize()).thenReturn(15);
        sut.book(criteria);
    }

    @DataProvider
    public final Object[][] getData() {
        return new Object[][] {
            {10, 15, 13, ID_CLASSROOM_2},
            {20, 25, 23, ID_CLASSROOM_2},
            {25, 20, 23, ID_CLASSROOM_1},
        };
    }

    // more cases with room 1
    // more cases - with classroom 2 selection
    // should book available room respecting size criterion
    @Test (dataProvider = "getData")
    public void shouldBookLargeEnoughClassroom(int sizeRoom1, int sizeRoom2, int sizeRequested, int bookedRoomId) {
        when(classroom1.getSize()).thenReturn(sizeRoom1);
        when(classroom2.getSize()).thenReturn(sizeRoom2);
        Criteria criteria = mock(Criteria.class);
        when(criteria.getTime()).thenReturn(VALID_TIME);
        when(criteria.getSize()).thenReturn(sizeRequested);
        sut.book(criteria);
        assertFalse(sut.getAvailableClassroomsIds(VALID_TIME).contains(bookedRoomId));
    }


    private HourOfWeek mockTime(List<HourOfWeek> allDays, HourOfWeek.Weekday weekday, Hour hour) {
        HourOfWeek time = mock(HourOfWeek.class);
        when(time.getHour()).thenReturn(hour);
        when(time.getWeekday()).thenReturn(weekday);
        allDays.add(time);
        return time;
    }

}
