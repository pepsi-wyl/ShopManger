package Dormitory_M.Service.user;

import Dormitory_M.DAO.BaseDao;
import Dormitory_M.DAO.user.userDaoImpl;
import Dormitory_M.Pojp.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("all")
public class userServiceImpl implements userService {

    /**
     * 内置DAO层userDao
     */
    private final userDaoImpl userDao = new userDaoImpl();

    /**
     * 用户登陆
     */
    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        try {
            connection = userDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDao.closeResource(connection, null, null);
        }
        if (user != null) {
            if (user.getUserPassword().equals(password) == true)          //密码逻辑判断
                return user;
        }
        return null;
    }

    /**
     * 修改密码
     */
    @Override
    public boolean updatePwd(String userCode, String password) {
        Connection connection = null;
        try {
            connection = userDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (userDao.updatePwd(connection, userCode, password) > 0);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();              //数据回滚
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);      //设置自动提交为true
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            userDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 得到用户数量
     */
    @Override
    public int getUserCount(String username, int userRole) {
        Connection connection = null;
        try {
            connection = userDao.getConnection();
            return userDao.getUserCount(connection, username, userRole);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDao.closeResource(connection, null, null);
        }
        return 0;
    }

    /**
     * 根据条件查询用户列表
     */
    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = userDao.getConnection();
            return userDao.getUserList(connection, queryUserName, queryUserRole, currentPageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 查询用户是否存在
     */
    @Override
    public boolean userCodeIsExist(String userCode) {
        Connection connection = null;
        try {
            connection = userDao.getConnection();
            return userDao.getLoginUser(connection, userCode).getId() != null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 添加用户
     */
    @Override
    public boolean addUser(User user) {
        Connection connection = null;
        try {
            connection = userDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (userDao.addUser(connection, user) > 0);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();              //数据回滚
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);      //设置自动提交为true
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            userDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 查看用户By_ID
     */
    @Override
    public User getUserByID(int id) {
        Connection connection = null;
        try {
            connection = userDao.getConnection();
            return userDao.getUserByID(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 修改用户By_ID
     */
    @Override
    public boolean modifyUserByID(User user) {
        Connection connection = null;
        try {
            connection = userDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (userDao.modifyUserByID(connection, user) > 0);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();              //数据回滚
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);      //设置自动提交为true
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            userDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 删除用户By_ID
     */
    @Override
    public boolean deleteUserByID(int id) {
        Connection connection = null;
        try {
            connection = userDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (userDao.deleteUserByID(connection, id)) > 0;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();              //数据回滚
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);      //设置自动提交为true
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            userDao.closeResource(connection, null, null);
        }
        return false;
    }

}
