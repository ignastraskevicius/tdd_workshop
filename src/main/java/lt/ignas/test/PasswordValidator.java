package lt.ignas.test;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/21/13
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordValidator {
    public boolean validate(String password) {
        boolean isValid;
        if(containsNumericChar(password) && containsAlphbethicChar(password)) {
            isValid = password.length() >= 6;
        } else {
            isValid = false;
        }
        return isValid;
    }

    private boolean containsNumericChar(String string) {
        return string.matches(".*[0-9].*");
    }

    private boolean containsAlphbethicChar(String string) {
        return string.matches(".*[A-Za-z].*");
    }
}
