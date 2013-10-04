package lt.ignas.classroombooking;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/28/13
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */

public class ReservationPlace   {

    private List<Classroom> bookableClassrooms = new ArrayList<Classroom>();
    private Map<HourOfWeek, List<Classroom>> map = new HashMap<HourOfWeek, List<Classroom>>();
    private TimeProvider provider;


    public List<Integer> getAllClassroomsIds() {
        return extractIds(bookableClassrooms);
    }

    public List<Integer> getAvailableClassroomsIds(HourOfWeek hourOfWeek) {
        return extractIds(map.get(hourOfWeek));
    }

    public void book(int classroomId, HourOfWeek hourOfWeek) {

       List<Classroom> availableClassrooms = map.get(hourOfWeek);
        if(isClassroomBooked(classroomId, hourOfWeek)) {
            throw new IllegalStateException("booked");
        }
       removeWithId(availableClassrooms, classroomId);
    }

    public void setBookableClassrooms(List<Classroom> bookableClassrooms) {
        this.bookableClassrooms = bookableClassrooms;

        for(HourOfWeek hourOfWeek: provider.values()) {
            map.put(hourOfWeek, new ArrayList<Classroom>(this.bookableClassrooms));
        }
    }

    public void setProvider(TimeProvider provider) {
        this.provider = provider;
    }

    public void book(final Criteria criteria) {
        Classroom classroom = findClassroomLargerThanSize(map.get(provider.values().get(1)), criteria.getSize());
        if(classroom != null) {
            book(classroom.getId(), criteria.getTime());
        } else {
            throw new IllegalStateException( "unavailable") ;
        }
    }

    private List<Integer> extractIds(List<Classroom> classroomList) {
        Iterable allClassromIds = Iterables.transform(classroomList, new Function<Classroom, Integer>() {
            @Override
            public Integer apply(lt.ignas.classroombooking.Classroom classroom) {

                return classroom.getId();
            }
        });
        return Lists.<Integer>newArrayList(allClassromIds);
    }

    private void removeWithId(List<Classroom> classroomList, final int classroomId) {
        Iterables.removeIf(classroomList, new Predicate<Classroom>() {
            @Override
            public boolean apply(lt.ignas.classroombooking.Classroom classroom) {
                return classroom.getId() == classroomId;
            }
        });
    }

    private Classroom findClassroomLargerThanSize(Collection<Classroom> classroomList, final int size) {
        Classroom classroom = Iterables.find(classroomList, new Predicate<Classroom>() {
            @Override
            public boolean apply(lt.ignas.classroombooking.Classroom classroom) {
                return classroom.getSize() > size;
            }
        }, null);
        return classroom;
    }

    private boolean isClassroomBooked(int classroomId, HourOfWeek time) {
        return !extractIds(map.get(time)).contains(classroomId);
    }
}
