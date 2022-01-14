package Dormitory_M.Servlet.user;

import Dormitory_M.Pojp.User;
import Dormitory_M.Service.user.userServiceImpl;
import Dormitory_M.Util.ConstantsUtil;
import Dormitory_M.Util.CookieUtils;
import Dormitory_M.Util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class LoginServlet extends HttpServlet {

    /**
     * 内置Service层userService
     */
    private userServiceImpl userService = new userServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * LoginServlet  登陆
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Login================");
        String userCode = req.getParameter("userCode").trim();
        String userPassword = req.getParameter("userPassword").trim();            //密码去除空格
        String vcode = req.getParameter("vcode").toUpperCase(Locale.ROOT);
        String loginCpacha = ((String) req.getSession().getAttribute(ConstantsUtil.LOGIN_CPACHA)).toUpperCase(Locale.ROOT);
        System.out.println(userCode + " " + userPassword + " " + vcode + " " + loginCpacha); //输出信息
        User user = userService.login(userCode, userPassword);       //获得用户信息
        if (StringUtil.isEmpty(vcode) || !vcode.equals(loginCpacha)) {   //验证码位空或者输入的验证码与Session中的不符合
            req.setAttribute("error", "验证码错误!");
            System.out.println("验证码错误");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        } //验证码输入正确
        if (null != user) {               //可以登陆
            req.getSession().setAttribute(ConstantsUtil.USER_SESSION, user);
            CookieUtils.creatCookie("username", userCode, 60 * 60, resp);  //免用户名登陆
            System.out.println("登陆成功");
            resp.sendRedirect("./jsp/frame.jsp");
        } else {
            req.setAttribute("error", "name错误或者password错误");
            System.out.println("name错误或者password错误");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

}


