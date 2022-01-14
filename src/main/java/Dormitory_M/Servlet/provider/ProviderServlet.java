package Dormitory_M.Servlet.provider;

import Dormitory_M.Pojp.Provider;
import Dormitory_M.Pojp.User;
import Dormitory_M.Service.bill.billServiceImpl;
import Dormitory_M.Service.provider.providerServiceImpl;
import Dormitory_M.Util.ConstantsUtil;
import Dormitory_M.Util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ProviderServlet extends HttpServlet {

    /**
     * 内置ProviderService对象
     * 内置billService对象
     */
    private providerServiceImpl providerService = new providerServiceImpl();
    private billServiceImpl billService = new billServiceImpl();

    /**
     * CpachaServlet method
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println("method: " + method);
        if (!StringUtil.isEmpty(method)) {
            switch (method) {
                case "query" -> query(req, resp);                                //查询供应商列表
                case "view" -> viewByID(req, resp, "providerview.jsp");      //查看供应商详细信
                case "add" -> add(req, resp);                                    //添加供应商信息
                case "ucexistProCode" -> ucexistProCode(req, resp);              //查看该供应商编码是否存在
                case "ucexistProName" -> ucexistProName(req, resp);              //查看该供应商名称是否存在
                case "modify" -> viewByID(req, resp, "providermodify.jsp");   //将修改供应商的信息展现出来
                case "modified" -> modify(req, resp);                             //修改供应商信息
                case "delprovider" -> delprovider(req, resp);                     //删除供应商信息
                default -> resp.getWriter().write("error method");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 查询供应商
     */
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * 获取供应商编码 和 供应商名称
         */
        String queryProCode = req.getParameter("queryProCode");
        String queryProName = req.getParameter("queryProName");
        if (queryProCode == null) {
            queryProCode = "";
        }
        if (queryProName == null) {
            queryProName = "";
        }

        /**
         获取查询页面PageIndex
         */
        int currentPageNo = 1; //默认为1
        String currentPageNoTemp = req.getParameter("pageIndex");
        if (!StringUtil.isEmpty(currentPageNoTemp)) {
            currentPageNo = Integer.parseInt(currentPageNoTemp); //解析该数字
        }

        /**
         * 获得分页支持
         */
        int pageSize = ConstantsUtil.PAGE_SIZE;   //页面显示的数量
        //查询该条件的总数
        int providerCount = providerService.getProviderCount(queryProCode, queryProName);
        //分页的数量
        int pageSupportTotalCount = (providerCount % pageSize) == 0 ? (int) (providerCount / pageSize) : (int) (providerCount / pageSize) + 1;
        //查询复合该条件的providerList
        List<Provider> providerList = providerService.getProviderList(queryProCode, queryProName, currentPageNo, pageSize);

        /**
         * 设置参数
         */
        req.setAttribute("providerList", providerList);
        req.setAttribute("queryProCode", queryProCode);
        req.setAttribute("queryProName", queryProName);
        req.setAttribute("totalCount", providerCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", pageSupportTotalCount);
        req.getRequestDispatcher("providerlist.jsp").forward(req, resp);
    }

    /**
     * 查看供应商详细信息By_ID
     * 该信息保存至修改页面上
     */
    public void viewByID(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("proid"));
        System.out.println("providerID: " + id);
        Provider providerByID = providerService.getProviderByID(id);
        req.setAttribute("provider", providerByID);
        req.getRequestDispatcher(url).forward(req, resp);
    }

    /**
     * 添加供应商信息
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Provider provider = new Provider();
        provider.setProCode(req.getParameter("proCode"));
        provider.setProName(req.getParameter("proName"));
        provider.setProContact(req.getParameter("proContact"));
        provider.setProPhone(req.getParameter("proPhone"));
        provider.setProAddress(req.getParameter("proAddress"));
        provider.setProFax(req.getParameter("proFax"));
        provider.setProDesc(req.getParameter("proDesc"));
        provider.setCreationDate(new Date());
        provider.setCreatedBy(((User) req.getSession().getAttribute(ConstantsUtil.USER_SESSION)).getId());
        System.out.println(provider);
        if (providerService.addProvider(provider)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
        } else {
            req.getRequestDispatcher("provideradd.jsp").forward(req, resp);
        }
    }

    /**
     * 查看该供应商编码是否存在
     */
    private void ucexistProCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proCode = req.getParameter("proCode");
        if (StringUtil.isEmpty(proCode)) {
            resp.getWriter().write("null");  //输入为空
        } else if (providerService.getProviderByCode(proCode).getProCode() != null) {
            resp.getWriter().write("no");    //供应商编码存在
        } else {
            resp.getWriter().write("yes");   //输入符合要求
        }
    }

    /**
     * 查看该供应商名称是否存在
     */
    private void ucexistProName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proName = req.getParameter("proName");
        if (StringUtil.isEmpty(proName)) {
            resp.getWriter().write("null");     //输入为空
        } else if (providerService.getProviderByCode(proName).getProName() != null) {
            resp.getWriter().write("no");       //供应商名称存在
        } else {
            resp.getWriter().write("yes");      //输入符合要求
        }
    }

    /**
     * 修改供应商信息
     */
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        /**
         * 封装数据
         */
        Provider provider = new Provider();
        provider.setProCode(req.getParameter("proCode"));
        provider.setProName(req.getParameter("proName"));
        provider.setProContact(req.getParameter("proContact"));
        provider.setProPhone(req.getParameter("proPhone"));
        provider.setProAddress(req.getParameter("proAddress"));
        provider.setProFax(req.getParameter("proFax"));
        provider.setProDesc(req.getParameter("proDesc"));
        provider.setModifyBy(((User) req.getSession().getAttribute(ConstantsUtil.USER_SESSION)).getId());
        provider.setModifyDate(new Date());
        System.out.println(provider);

        /**
         * 执行操作
         */
        if (providerService.modifyByCodeAndName(provider)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
        } else {
            req.getRequestDispatcher("providermodify.jsp").forward(req, resp);
        }
    }

    /**
     * 删除供应商信息
     */
    private void delprovider(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer id = Integer.valueOf(req.getParameter("proid"));
        System.out.println(id);
        if (providerService.getProviderByID(id).getProCode() != null) {       //ID下有供应商
            if (billService.getByProID(id).getBillCode() == null) {          //供应商没有订单
                if (providerService.deleteById(id)) {                                  //删除成功
                    resp.getWriter().write("true");
                } else {                                                                //删除失败
                    resp.getWriter().write("false");
                }
            } else {                                                           //供应商下有订单
                resp.getWriter().write("exist");
            }
        } else {                                                          //ID下没有供应商
            resp.getWriter().write("notexist");
        }
    }




}
