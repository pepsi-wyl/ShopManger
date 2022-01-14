package Dormitory_M.Service.bill;

import Dormitory_M.Pojp.Bill;

import java.util.ArrayList;

public interface billService {

    /**
     * 根据供应商ID获取订单
     */
    Bill getByProID(int proId);

    /**
     * 分页查询订单列表
     */
    ArrayList<Bill> getBillList(String productName, int providerId, int isPayment, int currentPageNo, int pageSize);

    /**
     * 查询订单总个数
     */
    int getBillCount(String productName, int providerId, int isPayment);

    /**
     * 根据订单ID获取订单
     */
    Bill getByBillID(int billId);

    /**
     * 根据订单号获取订单
     */
    Bill getByBillCode(String billCode);

    /**
     * 添加订单
     */
    boolean add(Bill bill);

    /**
     * 修改订单By_ID
     */
    boolean modifyByID(Bill bill);

    /**
     * 删除订单By_ID
     */
    boolean deleteById(int id);
}
