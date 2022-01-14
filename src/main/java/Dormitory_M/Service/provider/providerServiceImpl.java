package Dormitory_M.Service.provider;

import Dormitory_M.DAO.provider.providerDaoImpl;
import Dormitory_M.Pojp.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class providerServiceImpl implements providerService {

    /**
     * 内置DAO层providerDao
     */
    private providerDaoImpl providerDao = new providerDaoImpl();

    /**
     * 得到供应商数量 (根据用户名 或者 角色)
     */
    @Override
    public int getProviderCount(String proCode, String proName) {
        Connection connection = null;
        System.out.println("proCode ---- > " + proCode);
        System.out.println("proName ---- > " + proName);
        try {
            connection = providerDao.getConnection();
            return providerDao.getProviderCount(connection, proCode, proName);     //执行数据库操作
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            providerDao.closeResource(connection, null, null);
        }
        return 0;
    }

    /**
     * 得到供应商分页列表
     */
    @Override
    public List<Provider> getProviderList(String proCode, String proName, int currentPageNo, int pageSize) {
        Connection connection = null;
        System.out.println("proCode ---- > " + proCode);
        System.out.println("proName ---- > " + proName);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = providerDao.getConnection();
            return providerDao.getProviderList(connection, proCode, proName, currentPageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            providerDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 查询用户信息By_ID
     */
    @Override
    public Provider getProviderByID(int id) {
        Connection connection = null;
        try {
            connection = providerDao.getConnection();
            return providerDao.getProviderByID(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            providerDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 添加供应商信息
     */
    @Override
    public boolean addProvider(Provider provider) {
        Connection connection = null;
        try {
            connection = providerDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (providerDao.addProvider(connection, provider)) > 0;
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
            providerDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 查询供应商信息By_Code
     */
    @Override
    public Provider getProviderByCode(String proCode) {
        Connection connection = null;
        try {
            connection = providerDao.getConnection();
            return providerDao.getProviderByCode(connection, proCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            providerDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 查询供应商信息By_Name
     */
    @Override
    public Provider getProviderByName(String proName) {
        Connection connection = null;
        try {
            connection = providerDao.getConnection();
            return providerDao.getProviderByName(connection, proName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            providerDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 修改供应商信息
     */
    @Override
    public boolean modifyByCodeAndName(Provider provider) {
        Connection connection = null;
        try {
            connection = providerDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (providerDao.modifyByCodeAndName(connection, provider)) > 0;
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
            providerDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 删除供应商信息
     */
    @Override
    public boolean deleteById(int id) {
        Connection connection = null;
        try {
            connection = providerDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (providerDao.deleteById(connection, id)) > 0;
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
            providerDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 获取供应商列表
     */
    @Override
    public ArrayList<Provider> getProviderList() {
        Connection connection = null;
        try {
            connection = providerDao.getConnection();
            return providerDao.getProviderList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            providerDao.closeResource(connection, null, null);
        }
        return null;
    }

}
