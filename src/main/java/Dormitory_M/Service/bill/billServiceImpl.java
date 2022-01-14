package Dormitory_M.Service.bill;

import Dormitory_M.DAO.bill.billDaoImpl;
import Dormitory_M.Pojp.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class billServiceImpl implements billService {

    /**
     * 内置Dao层对象
     */
    private billDaoImpl billDao = new billDaoImpl();


    /**
     * 根据供应商ID获取订单
     */
    @Override
    public Bill getByProID(int proId) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            return billDao.getByProID(connection, proId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            billDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 分页查询订单列表
     */
    @Override
    public ArrayList<Bill> getBillList(String productName, int providerId, int isPayment, int currentPageNo, int pageSize) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            return billDao.getBillList(connection, productName, providerId, isPayment, currentPageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            billDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 查询订单总个数
     */
    @Override
    public int getBillCount(String productName, int providerId, int isPayment) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            return billDao.getBillCount(connection, productName, providerId, isPayment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            billDao.closeResource(connection, null, null);
        }
        return 0;
    }

    /**
     * 根据订单ID获取订单
     */
    @Override
    public Bill getByBillID(int billId) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            return billDao.getByBillID(connection, billId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            billDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 根据订单号获取订单
     */
    @Override
    public Bill getByBillCode(String billCode) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            return billDao.getByBillCode(connection, billCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            billDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 添加订单
     */
    @Override
    public boolean add(Bill bill) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (billDao.add(connection, bill) > 0);
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
            billDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 修改订单By_ID
     */
    @Override
    public boolean modifyByID(Bill bill) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (billDao.modifyByID(connection, bill) > 0);
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
            billDao.closeResource(connection, null, null);
        }
        return false;
    }

    /**
     * 删除订单By_ID
     */
    @Override
    public boolean deleteById(int id) {
        Connection connection = null;
        try {
            connection = billDao.getConnection();
            connection.setAutoCommit(false);           //设置自动提交为false
            return (billDao.deleteById(connection, id) > 0);
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
            billDao.closeResource(connection, null, null);
        }
        return false;
    }

}
