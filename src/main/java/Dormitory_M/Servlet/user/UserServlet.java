package Dormitory_M.Servlet.user;

import Dormitory_M.Pojp.Role;
import Dormitory_M.Pojp.User;
import Dormitory_M.Service.role.roleServiceImpl;
import Dormitory_M.Service.user.userServiceImpl;
import Dormitory_M.Util.ConstantsUtil;
import Dormitory_M.Util.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServlet extends HttpServlet {

    /**
     * 封装Service层对象
     */
    private userServiceImpl userService = new userServiceImpl();
    private roleServiceImpl roleService = new roleServiceImpl();

    /**
     * UserServlet method
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println("method： " + method);
        if (!StringUtil.isEmpty(method)) {
            switch (method) {
                case "pwdmodify" -> pwdModify(req, resp);                    //查询旧密码是否真实
                case "savepwd" -> updatePwd(req, resp);                      //修改密码
                case "query" -> query(req, resp);                            //查询用户列表
                case "ucexist" -> ucexist(req, resp);                        //查询账户是否存在
                case "getrolelist" -> getrolelist(req, resp);                //查询角色信息
                case "add" -> add(req, resp);                                //添加用户
                case "deluser" -> deluser(req, resp);                        //删除用户详细信息
                case "view" -> viewByID(req, resp, "userview.jsp");      //查看用户详细信息
                case "modify" -> viewByID(req, resp, "usermodify.jsp");  //修改用户详细信息(显示默认信息)
                case "modified" -> modify(req, resp);                        //修改用户详细信息
                default -> resp.getWriter().write("error method");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 修改密码
     */
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * 获取数据
         */
        User user = (User) req.getSession().getAttribute(ConstantsUtil.USER_SESSION);
        String newpassword = req.getParameter("newpassword").trim();   //去除密码收尾空格

        /**
         * 逻辑判断
         */
        if (user != null && !(StringUtils.isNullOrEmpty(newpassword))) {
            if (userService.updatePwd(user.getUserCode(), newpassword)) {
                req.setAttribute("message", "修改密码成功，请重新登陆!");
                req.getSession().removeAttribute(ConstantsUtil.USER_SESSION);
                resp.setHeader("refresh", "3;url=login.jsp");
            } else {
                req.setAttribute("message", "修改密码失败!");
            }
        } else {
            req.setAttribute("message", "修改密码失败，新密码有问题!");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    /**
     * 验证旧密码
     */
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        /**
         * 得到旧密码
         */
        String oldpassword = req.getParameter("oldpassword").trim();
        Object attribute = req.getSession().getAttribute(ConstantsUtil.USER_SESSION);

        /**
         *逻辑判断
         */
        if (attribute == null) {                                 //session过期
            resp.getWriter().write("sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {      //旧密码输入为空
            resp.getWriter().write("error");
        } else {
            String userPassword = ((User) attribute).getUserPassword();
            System.out.println("pwd" + oldpassword + " userPwd" + userPassword);
            if (oldpassword.equals(userPassword)) {
                resp.getWriter().write("true");
                System.out.println("true");
            } else {   //旧密码输入不正确
                resp.getWriter().write("false");
                System.out.println("false");
            }
        }
    }

    /**
     * 查询用户
     */
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         获得查询姓名
         */
        String queryname = req.getParameter("queryname");
        if (queryname == null) {       //查询姓名为空
            queryname = "";
        }

        /**
         获得查询角色
         */
        int queryUserRole = 0;
        String queryUserRoleTemp = req.getParameter("queryUserRole");
        if (queryUserRoleTemp != null && !"".equals(queryUserRoleTemp)) {     //查询角色不为空
            try {
                queryUserRole = Integer.parseInt(queryUserRoleTemp); //解析该角色
            } catch (NumberFormatException e) {
                try {
                    resp.sendRedirect("error.jsp");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        /**
         获取查询页面PageIndex
         */
        int currentPageNo = 1; //默认为1
        String currentPageNoTemp = req.getParameter("pageIndex");
        if (currentPageNoTemp != null) {
            try {
                currentPageNo = Integer.parseInt(currentPageNoTemp); //解析该数字
            } catch (NumberFormatException e) {
                try {
                    resp.sendRedirect("error.jsp");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        /**
         * 获得分页支持
         */
        int pageSize = ConstantsUtil.PAGE_SIZE;   //页面显示的数量
        //获取复合该条件的数量
        int userCount = userService.getUserCount(queryname, queryUserRole);
        //获取分页的总数
        int pageSupportTotalCount = (userCount % pageSize) == 0 ? (int) (userCount / pageSize) : (int) (userCount / pageSize) + 1;

        /**
         * 获取userList 和 roleList
         */
        List<User> userList = userService.getUserList(queryname, queryUserRole, currentPageNo, pageSize);
        List<Role> roleList = roleService.getRoleList();

        /**
         数据打包发送至前端
         */
        req.setAttribute("userList", userList);
        req.setAttribute("roleList", roleList);
        req.setAttribute("queryUserName", queryname);
        req.setAttribute("queryUserRole", queryUserRole);
        req.setAttribute("totalCount", userCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", pageSupportTotalCount);

        //转发到userList页面
        req.getRequestDispatcher("userlist.jsp").forward(req, resp);

    }

    /**
     * 验证用户是否存在
     */
    public void ucexist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userCode = req.getParameter("userCode");  //获取输入的编码
        System.out.println("userCode为空吗?" + StringUtil.isEmpty(userCode));
        System.out.println("userCode存在吗?" + userService.userCodeIsExist(userCode));
        /**
         * 逻辑判断 返回状态
         */
        if (StringUtil.isEmpty(userCode)) {
            resp.getWriter().write("null");
        } else if (userService.userCodeIsExist(userCode)) {
            resp.getWriter().write("no");
        } else {
            resp.getWriter().write("yes");
        }
    }

    /**
     * 得到角色列表
     */
    public void getrolelist(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        /**
         * 数据库操作
         */
        List<Role> roleList = roleService.getRoleList();  //得到用户角色列表
        System.out.println(roleList.toString());

        /**
         * 相应前端
         */
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));    //把roleList转换成json对象输出
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * 添加用户
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * 获取数据 和 数据封装
         */
        User user = new User();
        user.setUserCode(req.getParameter("userCode"));
        user.setUserName(req.getParameter("userName"));
        user.setUserPassword(req.getParameter("userPassword"));
        user.setGender(Integer.valueOf(req.getParameter("gender")));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(req.getParameter("phone"));
        user.setAddress(req.getParameter("address"));
        user.setUserRole(Integer.valueOf(req.getParameter("userRole")));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) req.getSession().getAttribute(ConstantsUtil.USER_SESSION)).getId());

        /**
         * 执行数据库操作 并返回前端页面
         */
        if (userService.addUser(user)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("useradd.jsp").forward(req, resp);
        }
    }

    /**
     * 查看用户ByID
     */
    public void viewByID(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {

        /**
         * 获取属性
         */
        int uid = Integer.parseInt(req.getParameter("uid"));
        System.out.println("UID: " + uid);

        /**
         * 执行数据库操作
         */
        User userByID = userService.getUserByID(uid);

        /**
         * 转发到网页
         */
        req.setAttribute("user", userByID);
        req.getRequestDispatcher(url).forward(req, resp);    //查看用户 修改用户
    }

    /**
     * 修改用户信息
     */
    public void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         *  获取属性 设置user对象的基本属性
         */
        User user = new User();
        user.setId(Integer.valueOf(req.getParameter("uid")));
        user.setUserName(req.getParameter("userName"));
        user.setGender(Integer.valueOf(req.getParameter("gender")));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(req.getParameter("phone"));
        user.setAddress(req.getParameter("address"));
        user.setUserRole(Integer.valueOf(req.getParameter("userRole")));
        user.setModifyBy(((User) req.getSession().getAttribute(ConstantsUtil.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        /**
         *执行数据库操作
         */
        if (userService.modifyUserByID(user)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }
    }

    /**
     * 删除用户信息
     */
    public void deluser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("uid"));
        System.out.println(id);
        if (userService.getUserByID(id) != null) {  //该用户存在
            if (userService.deleteUserByID(id)) {   //删除成功
                resp.getWriter().write("true");
            } else {                                //删除失败
                resp.getWriter().write("false");
            }
        } else {
            resp.getWriter().write("notexist");
        }
    }

}
