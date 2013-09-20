package lt.ignas.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/19/13
 * Time: 8:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyString {
    public static String reverse(String s) {
        List<String> tempArray = new ArrayList<String>(s.length());
        for(int i = 0; i < s.length(); i++) {
            tempArray.add(s.substring(i, i+1));
        }
        StringBuilder reverseString = new StringBuilder(s.length());
        for(int i = tempArray.size() - 1; i >= 0; i--) {
            reverseString.append(tempArray.get(i));
        }
        return reverseString.toString();
    }
}
