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
                {"111aaa222"}, {"ccc333"}
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
                {"12345"}, {"aa66"}
        };
    }

    //various cases and critical one
    //invalidate password with less than 6 chars long
    @Test(dataProvider = "getTooShortPasswords")
    public void shouldInvalidateAPasswordShorterThan6Chars(String shortPassword) {
        PasswordValidator sut = new PasswordValidator();
        Assert.assertFalse(sut.validate(shortPassword));
    }
    @DataProvider
    public static final Object[][] getPasswordsWithoutNumbers() {
        return new Object[][] {
            {"aaaaaa"},
            {"aaa***"},
            {"      "}
        };
    }

    // various cases without numbers. Symbols, letters, but no numbers. These passwords are bad.
    // password having no numbers is considered bad
    @Test(dataProvider = "getPasswordsWithoutNumbers")
    public void shouldInvalidatePasswordHavingNoNumber(String password) {
        PasswordValidator sut = new PasswordValidator();
        Assert.assertFalse(sut.validate(password));
    }

    @DataProvider
    public static final Object[][] getPasswordsWithoutLetters() {
        return new Object[][] {
            {"111222"},
            {"222###"},
            {"      "}
        };
    }

    // various cases of passwords having no letters. numbers, symbols, spaces
    // invalidate passwor which has no letters
    @Test(dataProvider = "getPasswordsWithoutLetters")
    public void shoudInvalidatePasswordHavingNoLetters(String password) {
        PasswordValidator sut = new PasswordValidator();
        Assert.assertFalse(sut.validate(password));
    }
}
