package lt.ignas.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/19/13
 * Time: 8:20 AM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class MyStringTest {
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNPEIfStringIsNull() {
        MyString.reverse(null);
    }

    public void shouldReverseEmptyString() {
        String reversed = MyString.reverse("");
        assertEquals(reversed, "");
    }

    @DataProvider
    public static final Object[][] getStringsAndReversedString() {
        return new Object[][] {
                {"abc", "cba"},
                {"This is test", "tset si sihT"},
                {"rats live on no evil star", "rats live on no evil star"}
        };
    }

    @Test(dataProvider = "getStringsAndReversedString")
    public void shouldReverseString_HappyPath(String word, String expectdReversed) {
        String reversed = MyString.reverse(word);
        assertEquals(reversed, expectdReversed);
    }
}
