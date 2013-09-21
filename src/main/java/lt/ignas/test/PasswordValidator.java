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
        return password.length() >= 6;
    }
}
