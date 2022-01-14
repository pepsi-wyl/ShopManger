package Dormitory_M.Servlet.cpacha;


import Dormitory_M.Util.ConstantsUtil;
import Dormitory_M.Util.CpachaUtil;
import Dormitory_M.Util.StringUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码Servlet
 */
public class CpachaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println(method);
        if (!StringUtil.isEmpty(method)) {
            switch (method) {
                case "loginCpacha" -> generateLoginCpacha(req, resp);
                default -> resp.getWriter().write("error method");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 得到登陆验证码
     */
    private void generateLoginCpacha(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("generateLoginCpacha================");
        CpachaUtil cpachaUtil = new CpachaUtil();
        String generatorVCode = cpachaUtil.generatorVCode();                 //获取验证码
        req.getSession().setAttribute(ConstantsUtil.LOGIN_CPACHA, generatorVCode);     //给Session设置验证码
        System.out.println("生成验证码： " + generatorVCode + "   " + " Session: " + req.getSession().getAttribute(ConstantsUtil.LOGIN_CPACHA));
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true); //得到旋转验证码
        ImageIO.write(generatorRotateVCodeImage, "gif", resp.getOutputStream());    //写出验证码
    }

}
