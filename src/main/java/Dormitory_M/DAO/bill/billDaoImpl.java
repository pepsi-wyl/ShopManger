package Dormitory_M.DAO.bill;

import Dormitory_M.DAO.BaseDao;
import Dormitory_M.Pojp.Bill;
import Dormitory_M.Util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class billDaoImpl extends BaseDao implements billDao {


    /**
                    * 查询结果集得到完整的对象
                    */
            private static Bill getBill(ResultSet rs) throws SQLException {
                Bill bill = new Bill();
                if (rs.next()) {
                    bill.setId(rs.getInt("id"));
                    bill.setBillCode(rs.getString("billCode"));
                    bill.setProductName(rs.getString("productName"));
                    bill.setProductDesc(rs.getString("productDesc"));
                    bill.setProductUnit(rs.getString("productUnit"));
                    bill.setProductCount(rs.getBigDecimal("productCount"));
                    bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                    bill.setIsPayment(rs.getInt("isPayment"));
                    bill.setCreatedBy(rs.getInt("createdBy"));
                    bill.setCreationDate(rs.getDate("creationDate"));
                    bill.setModifyBy(rs.getInt("modifyBy"));
                    bill.setModifyDate(rs.getDate("modifyDate"));
            bill.setProviderId(rs.getInt("providerId"));
            bill.setProviderName(rs.getString("proName"));
        }
        closeResource(null, null, rs);
        return bill;
    }

    /**
     * 查询结果集得到完整的对象集合
     */
    private static ArrayList<Bill> getBillList(ResultSet rs) throws SQLException {
        ArrayList<Bill> billList = new ArrayList<>();
        while (rs.next()) {
            Bill bill = new Bill();
            bill.setId(rs.getInt("id"));
            bill.setBillCode(rs.getString("billCode"));
            bill.setProductName(rs.getString("productName"));
            bill.setProductDesc(rs.getString("productDesc"));
            bill.setProductUnit(rs.getString("productUnit"));
            bill.setProductCount(rs.getBigDecimal("productCount"));
            bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
            bill.setIsPayment(rs.getInt("isPayment"));
            bill.setCreatedBy(rs.getInt("createdBy"));
            bill.setCreationDate(rs.getDate("creationDate"));
            bill.setModifyBy(rs.getInt("modifyBy"));
            bill.setModifyDate(rs.getDate("modifyDate"));
            bill.setProviderId(rs.getInt("providerId"));
            bill.setProviderName(rs.getString("proName"));
            billList.add(bill);
        }
        closeResource(null, null, rs);
        return billList;
    }

    /**
     * 根据供应商ID获取订单
     */
    @Override
    public Bill getByProID(Connection connection, int proId) throws Exception {
        if (connection != null) {
            String sql =
                    "select *\n" +
                            "from smbms_bill,\n" +
                            "     smbms_provider\n" +
                            "where smbms_provider.id = smbms_bill.providerId\n" +
                            "  and providerId = ?";
            ResultSet rs = query(connection, sql, proId);
            return getBill(rs);
        }
        return null;
    }

    /**
     * 分页查询订单列表
     */
    @Override
    public ArrayList<Bill> getBillList(Connection connection, String productName, int providerId, int isPayment, int currentPageNo, int pageSize) throws Exception {
        if (connection != null) {
            StringBuffer sql = new StringBuffer("select * from smbms_bill,smbms_provider where smbms_provider.id=smbms_bill.providerId");  //sql
            ArrayList<Object> parameter = new ArrayList<>();
            if (!StringUtil.isEmpty(productName)) {        //处理商品名称
                sql.append(" and productName like ?");
                parameter.add("%" + productName + "%");
            }
            if (providerId > 0) {
                sql.append(" and providerId=?");            //处理供应商
                parameter.add(providerId);
            }
            if (isPayment > 0) {
                sql.append(" and isPayment=?");             //处理是否付款
                parameter.add(isPayment);
            }
            /**
             * 处理分页要求
             */
            sql.append(" order by smbms_bill.creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            parameter.add(currentPageNo);
            parameter.add(pageSize);
            System.out.println("sql: " + sql.toString());
            ResultSet rs = query(connection, sql.toString(), parameter.toArray());
            return getBillList(rs);
        }
        return null;
    }

    /**
     * 查询订单总个数
     */
    @Override
    public int getBillCount(Connection connection, String productName, int providerId, int isPayment) throws Exception {
        if (connection != null) {
            StringBuffer sql = new StringBuffer("select count(1) count from smbms_bill where 1=1");  //sql
            ArrayList<Object> parameter = new ArrayList<>();
            if (!StringUtil.isEmpty(productName)) {        //处理商品名称
                sql.append(" and productName like ?");
                parameter.add("%" + productName + "%");
            }
            if (providerId > 0) {
                sql.append(" and providerId=?");            //处理供应商
                parameter.add(providerId);
            }
            if (isPayment > 0) {
                sql.append(" and isPayment=?");             //处理是否付款
                parameter.add(isPayment);
            }
            System.out.println("sql: " + sql.toString());
            ResultSet rs = query(connection, sql.toString(), parameter.toArray());
            if (rs.next()) {
                return rs.getInt("count");
            }
            closeResource(null, null, rs);
        }
        return 0;
    }

    /**
     * 根据订单ID获取订单
     */
    @Override
    public Bill getByBillID(Connection connection, int billId) throws Exception {
        if (connection != null) {
            String sql = "select *\n" +
                    "from smbms_bill,\n" +
                    "     smbms_provider\n" +
                    "where smbms_provider.id = smbms_bill.providerId\n" +
                    "  and smbms_bill.id = ?";
            ResultSet rs = query(connection, sql, billId);
            return getBill(rs);
        }
        return null;
    }

    /**
     * 根据订单号获取订单
     */
    @Override
    public Bill getByBillCode(Connection connection, String billCode) throws Exception {
        if (connection != null) {
            String sql = "select *\n" +
                    "from smbms_bill,\n" +
                    "     smbms_provider\n" +
                    "where smbms_provider.id = smbms_bill.providerId\n" +
                    "  and billCode = ?";
            ResultSet rs = query(connection, sql, billCode);
            return getBill(rs);
        }
        return null;
    }

    /**
     * 添加订单
     */
    @Override
    public int add(Connection connection, Bill bill) {
        if (connection != null) {
            String sql = "insert into smbms_bill (billCode,productName,productDesc," +
                    "productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            return update(connection, sql, bill.getBillCode(), bill.getProductName(), bill.getProductDesc(),
                    bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                    bill.getProviderId(), bill.getCreatedBy(), bill.getCreationDate());
        }
        return 0;
    }

    /**
     * 修改订单By_ID
     */
    @Override
    public int modifyByID(Connection connection, Bill bill) {
        if (connection != null) {
            String sql = "update smbms_bill set productName=?," +
                    "productDesc=?,productUnit=?,productCount=?,totalPrice=?," +
                    "isPayment=?,providerId=?,modifyBy=?,modifyDate=? where id = ? ";
            return update(connection, sql, bill.getProductName(), bill.getProductDesc(),
                    bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                    bill.getProviderId(), bill.getModifyBy(), bill.getModifyDate(), bill.getId());
        }
        return 0;
    }

    /**
     * 删除订单By_ID
     */
    @Override
    public int deleteById(Connection connection, int id) {
        if (connection != null) {
            String sql = "delete from smbms_bill where  id=?";
            return update(connection, sql, id);
        }
        return 0;
    }

}
