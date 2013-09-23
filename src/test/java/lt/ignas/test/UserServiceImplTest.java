package lt.ignas.test;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/23/13
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Test
public class UserServiceImplTest {
     //user gets password, updateUser is called
    @Test
    public void shouldSavePassword() throws Exception {
        SecurityService service = mock(SecurityService.class);
        User user = mock(User.class);
        UserDao dao = mock(UserDao.class);

        UserServiceImpl sut = new UserServiceImpl();
        sut.setService(service);
        sut.setDao(dao);

        String password = "pass";
        String hash = "hash";

        when(user.getPassword()).thenReturn(password);
        when(service.md5(password)).thenReturn(hash);

        sut.assignPassword(user);

        verify(user).setPassword(hash);
        verify(dao).updateUser(user);




    }
}
