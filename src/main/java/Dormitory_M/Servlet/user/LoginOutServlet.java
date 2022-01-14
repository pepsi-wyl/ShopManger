package Dormitory_M.Servlet.user;

import Dormitory_M.Util.ConstantsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginOutServlet extends HttpServlet {

    /**
     * LoginOutServlet 注销
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginOut================");
        if (req.getSession().getAttribute(ConstantsUtil.USER_SESSION) != null) {
            req.getSession().removeAttribute(ConstantsUtil.USER_SESSION);
        }
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
