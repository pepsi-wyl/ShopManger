package Dormitory_M.Service.user;

import Dormitory_M.Pojp.User;

import java.util.List;

public interface userService {

    /**
     * 用户登陆
     */
    User login(String userCode, String password);

    /**
     * 修改密码
     */
    boolean updatePwd(String userCode, String password);

    /**
     * 得到用户数量
     */
    int getUserCount(String username, int userRole);

    /**
     * 根据条件查询用户列表
     */
    List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

    /**
     * 查询用户是否存在
     */
    boolean userCodeIsExist(String userCode);

    /**
     * 添加用户
     */
    boolean addUser(User user);

    /**
     * 查看用户By_ID
     */
    User getUserByID(int id);

    /**
     * 修改用户By_ID
     */
    boolean modifyUserByID(User user);

    /**
     * 删除用户By_ID
     */
    boolean deleteUserByID(int id);

}
