package Dormitory_M.Servlet.bill;

import Dormitory_M.Pojp.Bill;
import Dormitory_M.Pojp.Provider;
import Dormitory_M.Pojp.User;
import Dormitory_M.Service.bill.billServiceImpl;
import Dormitory_M.Service.provider.providerServiceImpl;
import Dormitory_M.Util.ConstantsUtil;
import Dormitory_M.Util.StringUtil;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class BillServlet extends HttpServlet {

    /**
     * 内置providerService对象
     * 内置billService对象
     */
    private providerServiceImpl providerService = new providerServiceImpl();
    private billServiceImpl billService = new billServiceImpl();


    /**
     * BillServlet method
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println(method);
        if (!StringUtil.isEmpty(method)) {
            switch (method) {
                case "query" -> query(req, resp);                                 //查询订单信息列表
                case "view" -> viewByBillId(req, resp, "billview.jsp");       //查看订单信息
                case "add" -> add(req, resp);                                     //添加订单信息
                case "ucexistBillCode" -> ucexistBillCode(req, resp);             //验证订单号是否存在
                case "getproviderlist" -> getproviderlist(req, resp);             //获取供应商列表
                case "modify" -> viewByBillId(req, resp, "billmodify.jsp");   //修改订单信息并显示出来
                case "modifysave" -> modifysave(req, resp);                       //修改订单信息保存
                case "delbill" -> delBill(req, resp);                             //删除订单信息
                default -> resp.getWriter().write("error method");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 查询订单
     */
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * 获取和处理数据
         */
        String productName = req.getParameter("queryProductName");
        if (productName == null) {
            productName = "";
        }
        int providerId = 0;
        String providerIdTemp = req.getParameter("queryProviderId");
        if (!StringUtil.isEmpty(providerIdTemp)) {
            providerId = Integer.parseInt(providerIdTemp);
        }
        int isPayment = 0;
        String isPaymentTemp = req.getParameter("queryIsPayment");
        if (!StringUtil.isEmpty(isPaymentTemp)) {
            isPayment = Integer.parseInt(isPaymentTemp);
        }
        int currentPageNo = 1; //默认为1
        String currentPageNoTemp = req.getParameter("pageIndex");
        if (!StringUtil.isEmpty(currentPageNoTemp)) {
            currentPageNo = Integer.parseInt(currentPageNoTemp);
        }
        System.out.println("productName:" + productName + " providerId:" + providerId + " isPayment:" + isPayment + " currentPageNo:" + currentPageNo);

        /**
         * 分页
         */
        int pageSize = ConstantsUtil.PAGE_SIZE;   //页面显示的数量
        int billTotalCount = billService.getBillCount(productName, providerId, isPayment);
        int pageSupportTotalCount = (billTotalCount % pageSize) == 0 ? (int) (billTotalCount / pageSize) : (int) (billTotalCount / pageSize) + 1;

        /**
         * 填充数据
         */
        ArrayList<Bill> billList = billService.getBillList(productName, providerId, isPayment, currentPageNo, pageSize);
        ArrayList<Provider> providerList = providerService.getProviderList();

        /**
         * 设置参数
         */
        req.setAttribute("queryProductName", productName);
        req.setAttribute("queryProviderId", providerId);
        req.setAttribute("queryIsPayment", isPayment);
        req.setAttribute("providerList", providerList);
        req.setAttribute("billList", billList);
        req.setAttribute("totalCount", billTotalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", pageSupportTotalCount);
        req.getRequestDispatcher("billlist.jsp").forward(req, resp);
    }

    /**
     * 通过ID查看订单详细信息
     */
    public void viewByBillId(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        if (!StringUtil.isEmpty(req.getParameter("billid"))) {
            req.setAttribute("bill", billService.getByBillID(Integer.parseInt(req.getParameter("billid"))));
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }

    /**
     * 添加订单
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bill bill = new Bill();
        bill.setBillCode(req.getParameter("billCode"));
        bill.setProductName(req.getParameter("productName"));
        bill.setProductUnit(req.getParameter("productUnit"));
        bill.setProductDesc(req.getParameter("productDesc"));
        bill.setProductCount(new BigDecimal(req.getParameter("productCount")).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setTotalPrice(new BigDecimal(req.getParameter("totalPrice")).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(req.getParameter("providerId")));
        bill.setIsPayment(Integer.parseInt(req.getParameter("isPayment")));
        bill.setCreatedBy(((User) req.getSession().getAttribute(ConstantsUtil.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        if (billService.add(bill)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
        } else {
            req.getRequestDispatcher("billadd.jsp").forward(req, resp);
        }
    }

    /**
     * 查询订单号是否存在
     */
    public void ucexistBillCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String billCode = req.getParameter("billCode");
        System.out.println("billCode:" + billCode);
        if (StringUtil.isEmpty(billCode)) {
            resp.getWriter().write("null");  //输入为空
        } else if (billService.getByBillCode(billCode).getBillCode() != null) {
            resp.getWriter().write("no");    //订单号存在
        } else {
            resp.getWriter().write("yes");   //输入符合要求
        }
    }

    /**
     * 获取供应商列表
     */
    public void getproviderlist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 数据库操作
         */
        ArrayList<Provider> providerList = providerService.getProviderList();
        System.out.println(providerList.toString());
        /**
         * 相应前端
         */
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(providerList));       //把billList转换成json对象输出
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * 保存修改信息
     */
    public void modifysave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bill bill = new Bill();
        bill.setId(Integer.parseInt(req.getParameter("id")));
        bill.setProductName(req.getParameter("productName"));
        bill.setProductDesc(req.getParameter("productDesc"));
        bill.setProductUnit(req.getParameter("productUnit"));
        bill.setProductCount(new BigDecimal(req.getParameter("productCount")).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(req.getParameter("isPayment")));
        bill.setTotalPrice(new BigDecimal(req.getParameter("totalPrice")).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(req.getParameter("providerId")));
        bill.setModifyBy(((User) req.getSession().getAttribute(ConstantsUtil.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        if (billService.modifyByID(bill)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
        } else {
            req.getRequestDispatcher("billmodify.jsp").forward(req, resp);
        }
    }

    /**
     * 删除订单信息
     */
    public void delBill(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("billid"));
        System.out.println(id);
        if (billService.getByBillID(id).getId() != null) {   //订单ID存在
            if (billService.deleteById(id)) {                //删除成功
                resp.getWriter().write("true");
            } else {                                         //删除失败
                resp.getWriter().write("false");
            }
        } else {                                             //订单ID不存在
            resp.getWriter().write("notexist");
        }
    }


}
