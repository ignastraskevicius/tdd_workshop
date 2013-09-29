package lt.ignas.classroombooking;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/28/13
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReservationPlace {

    private List<Classroom> classrooms = new ArrayList<Classroom>();

    public ReservationPlace(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public List<Integer> getAllClassroomsIds(Weekday weekday) {

        Iterable allClassromIds = Iterables.transform(classrooms, new Function<Classroom, Integer>() {
            @Override
            public Integer apply(lt.ignas.classroombooking.Classroom classroom) {
                return classroom.getId();
            }
        });
        return Lists.<Integer>newArrayList(allClassromIds);
    }

    public void book(final int classroomId) {
        Iterables.removeIf(classrooms, new Predicate<Classroom>() {
            @Override
            public boolean apply(lt.ignas.classroombooking.Classroom classroom) {
                return classroom.getId() == classroomId;
            }
        });
    }


}
