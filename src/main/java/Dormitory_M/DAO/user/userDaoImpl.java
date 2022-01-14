package Dormitory_M.DAO.user;

import Dormitory_M.DAO.BaseDao;
import Dormitory_M.Pojp.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class userDaoImpl extends BaseDao implements userDao {

    /**
     * 得到用户对象  userCode
     */
    @Override
    public User getLoginUser(Connection connection, String userCode) throws Exception {
        User user = null;
        if (null != connection) {
            String sql = "select * from smbms_user where userCode=?";
            ResultSet rs = query(connection, sql, userCode);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            closeResource(null, null, rs);
        }
        return user;
    }

    /**
     * 修改用户密码  userCode pwd
     */
    @Override
    public int updatePwd(Connection connection, String uerCode, String pwd) {
        if (null != connection) {
            String sql = "update smbms_user set userPassword=? where userCode=?";
            return update(connection, sql, pwd, uerCode);
        }
        return 0;
    }

    /**
     * 得到用户数量 (根据用户名 或者 角色)
     */
    @Override
    public int getUserCount(Connection connection, String username, int userRole) throws Exception {
        int count = 0;
        if (null != connection) {
            StringBuffer sql = new StringBuffer("select count(1) count from smbms_user u,smbms_role r where u.userRole=r.id");
            ArrayList<Object> list = new ArrayList<>();
            if (!StringUtils.isNullOrEmpty(username)) {
                sql.append(" and u.userName like ?");       //模糊查询
                list.add("%" + username + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole=?");
                list.add(userRole);
            }
            System.out.println(sql);
            ResultSet rs = query(connection, sql.toString(), list.toArray());
            if (rs.next()) {
                count = rs.getInt("count");
            }
            closeResource(null, null, rs);
        }
        return count;
    }

    /**
     * 获取分页user列表
     */
    @Override
    public List<User> getUserList(Connection connection, String username, int userRole, int currentPageNo, int pageSize) throws Exception {
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<>();        //保存参数
            if (!StringUtils.isNullOrEmpty(username)) {
                sql.append(" and u.userName like ?");
                list.add("%" + username + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql);
            rs = query(connection, sql.toString(), params);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setUserRole(rs.getInt("userRole"));
                user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(user);
            }
            closeResource(null, null, rs);
        }
        return userList;
    }

    /**
     * 添加用户
     */
    @Override
    public int addUser(Connection connection, User user) {
        if (null != connection) {
            String sql = "insert into smbms_user (`userCode`, `userName`, `userPassword`, `gender`, " +
                    "`birthday`, `phone`, `address`, `userRole`,`createdBy`, `creationDate`)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return update(connection, sql, user.getUserCode(), user.getUserName(), user.getUserPassword(), user.getGender(),
                    user.getBirthday(), user.getPhone(), user.getAddress(), user.getUserRole(), user.getCreatedBy(), user.getCreationDate());
        }
        return 0;
    }

    /**
     * 查看用户By_ID
     */
    @Override
    public User getUserByID(Connection connection, int id) throws Exception {
        User user = null;
        if (connection != null) {
            String sql = "select *\n" +
                    "from smbms_user,\n" +
                    "     smbms_role\n" +
                    "where smbms_user.userRole = smbms_role.id and  smbms_user.id=?";
            ResultSet rs = query(connection, sql, id);
            if (rs.next()) {    //封装数据
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRoleName(rs.getString("roleName"));
                user.setUserRole(rs.getInt("userRole"));
            }
        }
        return user;
    }


    /**
     * 修改用户By_ID
     */
    @Override
    public int modifyUserByID(Connection connection, User user) {
        if (connection != null) {
            String sql = "update smbms_user\n" +
                    "set userName=?,\n" +
                    "    gender=?,\n" +
                    "    birthday=?,\n" +
                    "    address=?,\n" +
                    "    phone=?,\n" +
                    "    userRole=?,\n" +
                    "    modifyBy=?,\n" +
                    "    modifyDate=?\n" +
                    "where id = ?;";
            return update(connection, sql,
                    user.getUserName(), user.getGender(), user.getBirthday(), user.getAddress(),
                    user.getPhone(), user.getUserRole(), user.getModifyBy(), user.getModifyDate(),
                    user.getId());
        }
        return 0;
    }

    /**
     * 删除用户By_ID
     */
    @Override
    public int deleteUserByID(Connection connection, int id) {
        if (connection != null) {
            String sql = "delete  from smbms_user where id=?";
            return update(connection, sql, id);
        }
        return 0;
    }

}
