package Dormitory_M.DAO.role;

import Dormitory_M.DAO.BaseDao;
import Dormitory_M.Pojp.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class roleDaoImpl extends BaseDao implements roleDao {

    /**
     * 获取角色列表
     */
    @Override
    public List<Role> getRoleList(Connection connection) throws Exception {
        ResultSet resultSet = null;
        ArrayList<Role> list = new ArrayList<>();
        if (connection != null) {
            String sql = "select * from smbms_role";
            resultSet = query(connection, sql);
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setRoleCode(resultSet.getString("roleCode"));
                role.setRoleName(resultSet.getString("roleName"));
                list.add(role);
            }
            closeResource(null,null,resultSet);
        }
        return list;
    }

}
