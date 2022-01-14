package Dormitory_M.DAO.provider;

import Dormitory_M.Pojp.Provider;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface providerDao {

    /**
     * 得到供应商数量 (根据用户名 或者 角色)
     */
    int getProviderCount(Connection connection, String proCode, String proName) throws Exception;

    /**
     * 得到供应商分页列表
     */
    List<Provider> getProviderList(Connection connection, String proCode, String proName, int currentPageNo, int pageSize) throws Exception;

    /**
     * 查询供应商信息By_ID
     */
    Provider getProviderByID(Connection connection, int id) throws Exception;

    /**
     * 添加供应商信息
     */
    int addProvider(Connection connection, Provider provider);

    /**
     * 查询供应商信息By_Code
     */
    Provider getProviderByCode(Connection connection, String proCode) throws Exception;

    /**
     * 查询供应商信息By_Name
     */
    Provider getProviderByName(Connection connection, String proName) throws Exception;

    /**
     * 修改供应商信息
     */
    int modifyByCodeAndName(Connection connection, Provider provider);

    /**
     * 删除供应商信息
     */
    int deleteById(Connection connection, int id);

    /**
     * 获取供应商列表
     */
    ArrayList<Provider> getProviderList(Connection connection) throws Exception;


}
