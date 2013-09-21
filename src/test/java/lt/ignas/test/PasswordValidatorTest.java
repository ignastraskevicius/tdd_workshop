package lt.ignas.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/21/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class PasswordValidatorTest {

    @DataProvider
    public static final Object[][] getLongEnoughPasswords() {
        return new Object[][] {
                {"123456"}, {"abcdefghi"}
        };
    }

    //various cases and writical one
    //password is at least 6 characters long
    @Test(dataProvider = "getLongEnoughPasswords")
    public void shouldValidateAPasswordAtLeast6CharsLong(String longPassword) {
        PasswordValidator sut = new PasswordValidator();
        Assert.assertTrue(sut.validate(longPassword));
    }

    @DataProvider
    public static final Object[][] getTooShortPasswords() {
        return new Object[][] {
                {"12345"}, {"abcde"}
        };
    }

    //various cases and critical one
    //invalidate password with less than 6 chars long
    @Test(dataProvider = "getTooShortPasswords")
    public void shouldInvalidateAPasswordShorterThan6Chars(String shortPassword) {
        PasswordValidator sut = new PasswordValidator();
        Assert.assertFalse(sut.validate(shortPassword));
    }
}
