package Dormitory_M.Service.provider;

import Dormitory_M.Pojp.Provider;

import java.util.ArrayList;
import java.util.List;

public interface providerService {

    /**
     * 得到供应商数量 (根据用户名 或者 角色)
     */
    int getProviderCount(String proCode, String proName);

    /**
     * 得到供应商分页列表
     */
    List<Provider> getProviderList(String proCode, String proName, int currentPageNo, int pageSize);

    /**
     * 查询用户信息By_ID
     */
    Provider getProviderByID(int id);

    /**
     * 添加供应商信息
     */
    boolean addProvider(Provider provider);

    /**
     * 查询供应商信息By_Code
     */
    Provider getProviderByCode(String proCode);

    /**
     * 查询供应商信息By_Name
     */
    Provider getProviderByName(String proName);

    /**
     * 修改供应商信息
     */
    boolean modifyByCodeAndName(Provider provider);

    /**
     * 删除供应商信息
     */
    boolean deleteById(int id);

    /**
     * 获取供应商列表
     */
    ArrayList<Provider> getProviderList() ;


}
