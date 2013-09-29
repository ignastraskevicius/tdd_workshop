package lt.ignas.classroombooking;

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

    boolean flag = false;
    public ReservationPlace(List<Classroom> classrooms) {
        flag = true;

    }

    public ReservationPlace() {


    }

    public List<Integer> getAllClassroomsIds() {
        return flag ? Arrays.asList(1) : Collections.<Integer>emptyList();
    }
}
