package lt.ignas.classroombooking;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/28/13
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReservationPlace {

    private List<Classroom> classrooms = new ArrayList<Classroom>();
    Map<Weekday, List<Classroom>> map = new HashMap<Weekday, List<Classroom>>();


    public ReservationPlace(List<Classroom> classrooms) {
        this.classrooms = classrooms;
        for(Weekday weekday: Weekday.values()) {
            map.put(weekday, new ArrayList<Classroom>(classrooms));
        }
    }

    public List<Integer> getAllClassroomsIds() {
        return extractIds(classrooms);
    }

    public List<Integer> getAvailableClassroomsIds(Weekday weekday) {
        return extractIds(map.get(weekday));
    }

    public void book(int classroomId, Weekday weekday) {

       map.put(weekday, new ArrayList<Classroom>(classrooms));
       removeWithId(map.get(weekday), classroomId);
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
}
