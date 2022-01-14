package Dormitory_M.DAO.provider;

import Dormitory_M.DAO.BaseDao;
import Dormitory_M.Pojp.Provider;
import Dormitory_M.Util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class providerDaoImpl extends BaseDao implements providerDao {

    /**
     * 查询结果集得到完整的对象
     */
    private static Provider getProvider(ResultSet rs) throws SQLException {
        Provider provider = new Provider();
        if (rs.next()) {
            provider.setId(rs.getInt("id"));
            provider.setProCode(rs.getString("proCode"));
            provider.setProName(rs.getString("proName"));
            provider.setProDesc(rs.getString("proDesc"));
            provider.setProContact(rs.getString("proContact"));
            provider.setProPhone(rs.getString("proPhone"));
            provider.setProAddress(rs.getString("proAddress"));
            provider.setProFax(rs.getString("proFax"));
            provider.setCreatedBy(rs.getInt("createdBy"));
            provider.setCreationDate(rs.getTimestamp("creationDate"));
            provider.setModifyBy(rs.getInt("modifyBy"));
            provider.setModifyDate(rs.getTimestamp("modifyDate"));
        }
        closeResource(null, null, rs);
        return provider;
    }

    /**
     * 查询结果集得到完整的对象List
     */
    private static ArrayList<Provider> getProviderList(ResultSet rs) throws SQLException {
        ArrayList<Provider> providers = new ArrayList<>();
        while (rs.next()) {
            Provider provider = new Provider();
            provider.setId(rs.getInt("id"));
            provider.setProCode(rs.getString("proCode"));
            provider.setProName(rs.getString("proName"));
            provider.setProDesc(rs.getString("proDesc"));
            provider.setProContact(rs.getString("proContact"));
            provider.setProPhone(rs.getString("proPhone"));
            provider.setProAddress(rs.getString("proAddress"));
            provider.setProFax(rs.getString("proFax"));
            provider.setCreatedBy(rs.getInt("createdBy"));
            provider.setCreationDate(rs.getTimestamp("creationDate"));
            provider.setModifyBy(rs.getInt("modifyBy"));
            provider.setModifyDate(rs.getTimestamp("modifyDate"));
            providers.add(provider);
        }
        closeResource(null, null, rs);
        return providers;
    }


    /*******************************************************************
     * Dao方法
     */

    /**
     * 得到供应商数量 (根据用户名 或者 角色)
     */
    @Override
    public int getProviderCount(Connection connection, String proCode, String proName) throws Exception {
        int count = 0;
        if (connection != null) {
            StringBuffer sql = new StringBuffer("select count(1) count from smbms_provider where 1=1");
            ArrayList<String> list = new ArrayList<>();
            if (!StringUtil.isEmpty(proCode)) {      //数据过滤
                sql.append(" and proCode like ?");
                list.add("%" + proCode + "%");
            }
            if (!StringUtil.isEmpty(proName)) {      //数据过滤
                sql.append(" and proName like ?");
                list.add("%" + proName + "%");
            }
            System.out.println(sql.toString());
            ResultSet rs = query(connection, sql.toString(), list.toArray());
            if (rs.next()) {
                count = rs.getInt("count");
            }
            closeResource(null, null, rs);
        }
        return count;
    }

    /**
     * 得到供应商分页列表
     */
    @Override
    public List<Provider> getProviderList(Connection connection, String proCode, String proName, int currentPageNo, int pageSize) throws Exception {
        if (connection != null) {
            StringBuffer sql = new StringBuffer("select * from smbms_provider where 1=1");
            ArrayList<Object> dateList = new ArrayList<>();        //数据列表
            if (!StringUtil.isEmpty(proCode)) {      //数据过滤
                sql.append(" and proCode like ?");
                dateList.add("%" + proCode + "%");   //模糊查询
            }
            if (!StringUtil.isEmpty(proName)) {      //数据过滤
                sql.append(" and proName like ?");
                dateList.add("%" + proName + "%");    //模糊查询
            }
            sql.append(" order by creationDate DESC limit ?,?");  //查询特定数量的数据
            currentPageNo = (currentPageNo - 1) * pageSize;
            dateList.add(currentPageNo);
            dateList.add(pageSize);
            System.out.println("sql ----> " + sql);
            ResultSet rs = query(connection, sql.toString(), dateList.toArray());
            return getProviderList(rs);   //得到对象集合
        }
        return null;
    }

    /**
     * 查询用户信息By_ID
     */
    @Override
    public Provider getProviderByID(Connection connection, int id) throws Exception {
        if (connection != null) {
            String sql = "select * from smbms_provider where id=?";
            ResultSet rs = query(connection, sql, id);
            return getProvider(rs);
        }
        return null;
    }

    /**
     * 添加供应商信息
     */
    @Override
    public int addProvider(Connection connection, Provider provider) {
        if (connection != null) {
            String sql = "insert into smbms_provider\n" +
                    "(proCode, proName, proContact, proPhone, proAddress, proFax, proDesc,\n" +
                    " creationDate, createdBy)\n" +
                    "    value (?, ?, ?, ?, ?, ?, ?, ?,?)";
            return update(connection, sql, provider.getProCode(), provider.getProName(), provider.getProContact(), provider.getProPhone(),
                    provider.getProAddress(), provider.getProFax(), provider.getProDesc(), provider.getCreationDate(), provider.getCreatedBy());
        }
        return 0;
    }

    /**
     * 查询供应商信息By_Code
     */
    @Override
    public Provider getProviderByCode(Connection connection, String proCode) throws Exception {
        if (connection != null) {
            String sql = "select * from smbms_provider where proCode=?";
            ResultSet rs = query(connection, sql, proCode);
            return getProvider(rs);
        }
        return null;
    }

    /**
     * 查询供应商信息By_Name
     */
    @Override
    public Provider getProviderByName(Connection connection, String proName) throws Exception {
        if (connection != null) {
            String sql = "select * from smbms_provider where proName=?";
            ResultSet rs = query(connection, sql, proName);
            return getProvider(rs);
        }
        return null;
    }

    /**
     * 修改供应商信息
     */
    @Override
    public int modifyByCodeAndName(Connection connection, Provider provider) {
        if (connection != null) {
            String sql = "update smbms_provider\n" +
                    "set proContact=?,\n" +
                    "    proPhone=?,\n" +
                    "    proAddress=?,\n" +
                    "    proFax=?,\n" +
                    "    proDesc=?,\n" +
                    "    modifyBy=?,\n" +
                    "    modifyDate=?\n" +
                    "where proCode = ?\n" +
                    "  and proName = ?;";
            return update(connection, sql,
                    provider.getProContact(), provider.getProPhone(), provider.getProAddress(), provider.getProFax(),
                    provider.getProDesc(), provider.getModifyBy(), provider.getModifyDate(),
                    provider.getProCode(), provider.getProName());
        }
        return 0;
    }

    /**
     * 删除供应商信息
     */
    @Override
    public int deleteById(Connection connection, int id) {
        if (connection != null) {
            String sql = "delete\n" +
                    "from smbms_provider\n" +
                    "where id = ?";
            return update(connection, sql, id);
        }
        return 0;
    }

    /**
     * 获取供应商列表
     */
    @Override
    public ArrayList<Provider> getProviderList(Connection connection) throws Exception {
        if (connection != null) {
            String sql = "select * from smbms_provider";
            ResultSet rs = query(connection, sql);
            return getProviderList(rs);
        }
        return null;
    }

}
