package lt.ignas.test;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/23/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface User {
    String getPassword();

    void setPassword(String passwordMd5);
}
