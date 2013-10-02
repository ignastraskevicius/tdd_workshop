package lt.ignas.classroombooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    List<HourOfWeek> possibleTimes = new ArrayList<HourOfWeek>();

    HourOfWeek sunday8AM = mockTime(possibleTimes, HourOfWeek.Weekday.SUNDAY, Hour.AM_8);
    HourOfWeek sunday6PM = mockTime(possibleTimes, HourOfWeek.Weekday.SUNDAY, Hour.PM_6);
    HourOfWeek monday8AM = mockTime(possibleTimes, HourOfWeek.Weekday.MONDAY, Hour.AM_8);
    HourOfWeek monday6PM = mockTime(possibleTimes, HourOfWeek.Weekday.MONDAY, Hour.PM_6);
    HourOfWeek VALID_TIME  = sunday8AM;

    TimeProvider timeProvider;

    private static Logger logger = LoggerFactory.getLogger(ReservationPlaceTest.class.getName());

    @BeforeMethod
    public void setUp() {
        timeProvider = mock(TimeProvider.class);
        when(timeProvider.values()).thenReturn(possibleTimes);

        classroom1 = mock(Classroom.class);
        when(classroom1.getId()).thenReturn(ID_CLASSROOM_1);
        classroom2 = mock(Classroom.class);
        when(classroom2.getId()).thenReturn(ID_CLASSROOM_2);

        sut = new ReservationPlace();

        sut.setProvider(timeProvider);
        sut.setBookableClassrooms(new ArrayList(asList(classroom1, classroom2)));
    }

    @Test
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

    @Test
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
        sut.book(id, monday8AM);
        assertFalse(sut.getAvailableClassroomsIds(monday8AM).contains(id));
    }

    @Test
    public void notBookedClasseoomShouldBeAvailable() {
        sut.book(ID_CLASSROOM_2, monday8AM);
        assertTrue(sut.getAvailableClassroomsIds(monday8AM).contains(ID_CLASSROOM_1));
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
        assertNotNull(sunday8AM);
        assertNotNull(time);
        assertEquals(sut.getAvailableClassroomsIds(time), asList(ID_CLASSROOM_1, ID_CLASSROOM_2));
    }

    @Test
    public void bookingClassromShouldNotRemoveFromAllClassroomList() {
        sut.book(ID_CLASSROOM_2, monday8AM);
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


    private HourOfWeek[] toArray(Collection<HourOfWeek> collection) {
        return collection.toArray(new HourOfWeek[0]);
    }

    private HourOfWeek mockTime(List<HourOfWeek> allDays, HourOfWeek.Weekday weekday, Hour hour) {
        HourOfWeek time = mock(HourOfWeek.class);
        when(time.getHour()).thenReturn(hour);
        when(time.getWeekday()).thenReturn(weekday);
        allDays.add(time);
        return time;
    }
}
