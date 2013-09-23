package lt.ignas.test;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/23/13
 * Time: 8:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl {
    private UserDao userDao;
    private SecurityService securityService;

    public void assignPassword(User user) throws Exception {
        String passwordMd5 = securityService.md5(user.getPassword());
        user.setPassword(passwordMd5);
        userDao.updateUser(user);
    }

    public void setService(SecurityService service) {
        this.securityService = service;
    }

    public void setDao(UserDao dao) {
        this.userDao = dao;
    }
}
