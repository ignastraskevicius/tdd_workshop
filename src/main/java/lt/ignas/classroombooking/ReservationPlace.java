package lt.ignas.classroombooking;

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

    public ReservationPlace() {


    }

    public List<Integer> getAllClassroomsIds() {
        return classrooms.isEmpty() ?
        Collections.<Integer>emptyList() : Arrays.asList(classrooms.get(0).getId());
    }
}
