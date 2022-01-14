package Dormitory_M.DAO.user;

import Dormitory_M.Pojp.User;

import java.sql.Connection;
import java.util.List;

public interface userDao {

    /**
     * 得到用户对象  userCode
     */
    User getLoginUser(Connection connection, String userCode) throws Exception;

    /**
     * 修改用户密码  userCode pwd
     */
    int updatePwd(Connection connection, String uerCode, String pwd);

    /**
     * 得到用户数量 (根据用户名 或者 角色)
     */
    int getUserCount(Connection connection, String username, int userRole) throws Exception;

    /**
     * 获取分页user列表
     */
    List<User> getUserList(Connection connection, String username, int userRole, int currentPageNO, int pageSize) throws Exception;

    /**
     * 添加用户
     */
    int addUser(Connection connection, User user);

    /**
     * 查看用户By_ID
     */
    User getUserByID(Connection connection, int id) throws Exception;

    /**
     * 修改用户By_ID
     */
    int modifyUserByID(Connection connection, User user);

    /**
     * 删除用户By_ID
     */
    int deleteUserByID(Connection connection, int id);
}
