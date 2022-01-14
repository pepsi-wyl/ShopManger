package Dormitory_M.Service.role;

import Dormitory_M.DAO.role.roleDaoImpl;
import Dormitory_M.Pojp.Role;

import java.sql.Connection;
import java.util.List;

public class roleServiceImpl implements roleService {

    /**
     * 内置DAO层roleDao
     */
    private roleDaoImpl roleDao = new roleDaoImpl();

    /**
     * 获取角色列表
     */
    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        try {
            connection = roleDaoImpl.getConnection();
            return roleDao.getRoleList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            roleDao.closeResource(connection, null, null);
        }
        return null;
    }


}
