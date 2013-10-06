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
        List<Classroom> classroomzz = findClassroomsLargerThanSize(map.get(criteria.getTime()), criteria.getSize());
        Classroom classroom;
        if(criteria.getEquipment() != null) {
            classroom = findClassroomsWithEquipment(classroomzz, criteria.getEquipment());
            if(classroom == null) {
                throw new IllegalStateException("no classroom having requested equipment");
            }
        } else {
            classroom = classroomzz.isEmpty() ? null : classroomzz.get(0);
        }
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

    private List<Classroom> findClassroomsLargerThanSize(Collection<Classroom> classroomList, final int requestedSize) {
        Iterable<Classroom> classrooms = Iterables.filter(classroomList, new Predicate<Classroom>() {
            @Override
            public boolean apply(lt.ignas.classroombooking.Classroom classroom) {
                return classroom.getSize() >= requestedSize;
            }
        });
        return Lists.newArrayList(classrooms);
    }

    private Classroom findClassroomsWithEquipment(Collection<Classroom> classroomList, final Equipment requestedEquipment) {
        Classroom classroom = Iterables.find(classroomList, new Predicate<Classroom>() {
            @Override
            public boolean apply(lt.ignas.classroombooking.Classroom classroom) {
                return classroom.getEquipment() == requestedEquipment;
            }
        }, null);
        return classroom;
    }

    private boolean isClassroomBooked(int classroomId, HourOfWeek time) {
        return !extractIds(map.get(time)).contains(classroomId);
    }
}
