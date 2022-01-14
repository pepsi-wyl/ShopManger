package Dormitory_M.DAO.role;

import Dormitory_M.Pojp.Role;

import java.sql.Connection;
import java.util.List;

public interface roleDao {
    /**
     * 获取角色列表
     */
    List<Role> getRoleList(Connection connection) throws Exception;
}
