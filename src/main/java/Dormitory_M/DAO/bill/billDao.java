package Dormitory_M.DAO.bill;

import Dormitory_M.Pojp.Bill;

import java.sql.Connection;
import java.util.ArrayList;

public interface billDao {

    /**
     * 根据供应商ID获取订单
     */
    Bill getByProID(Connection connection, int proId) throws Exception;

    /**
     * 分页查询订单列表
     */
    ArrayList<Bill> getBillList(Connection connection, String productName, int providerId, int isPayment, int currentPageNo, int pageSize) throws Exception;

    /**
     * 查询订单总个数
     */
    int getBillCount(Connection connection, String productName, int providerId, int isPayment) throws Exception;

    /**
     * 根据订单ID获取订单
     */
    Bill getByBillID(Connection connection, int billId) throws Exception;

    /**
     * 根据订单号获取订单
     */
    Bill getByBillCode(Connection connection, String billCode) throws Exception;

    /**
     * 添加订单
     */
    int add(Connection connection, Bill bill);

    /**
     * 修改订单By_ID
     */
    int modifyByID(Connection connection, Bill bill);

    /**
     * 删除订单By_ID
     */
    int deleteById(Connection connection, int id);

}
