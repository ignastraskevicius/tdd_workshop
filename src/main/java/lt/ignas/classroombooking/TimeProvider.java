package lt.ignas.classroombooking;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 10/1/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TimeProvider {
    List<HourOfWeek> values();
}
