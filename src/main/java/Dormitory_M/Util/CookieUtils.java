package Dormitory_M.Util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CookieUtils  Design By wyl(zhazha)
 * 2021年9月2日 星期四 21:30
 * Cookie 是服务器通知客户端保存键值对的一种技术
 * 客户端有了 Cookie 后，每次请求都发送给服务器
 * 每个 Cookie 的大小不能超过 4kb
 */

public class CookieUtils {


    /**
     * creatCookie
     * 参数 key value resp
     * new Cookie对象
     * resp.addCookie 响应
     */

    /**
     * 默认存活时间 time=-1
     * 存活周期Session
     */
    public static Boolean creatCookie(String key, String value, HttpServletResponse resp) {
        if (!StringUtil.isEmpty(key) && !StringUtil.isEmpty(value) && resp != null) {
            //创建 Cookie对象    通知客户端保存Cookie
            resp.addCookie(new Cookie(key, value));
            return true;
        }
        return false;
    }

    /**
     * 存活时间time>0    分钟
     */
    public static Boolean creatCookie(String key, String value, int time, HttpServletResponse resp) {
        if (!StringUtil.isEmpty(key) && !StringUtil.isEmpty(value) && time > 0 && resp != null) {
            //创建 Cookie对象    通知客户端保存Cookie
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(time);
            resp.addCookie(cookie);
            return true;
        }
        return false;
    }

    /**
     * getCookies
     * req.getCookies
     */
    public static Cookie[] getCookies(HttpServletRequest req) {
        return req == null ? null : req.getCookies();
    }

    /**
     * findCookie
     * forEach循环查找 为name的cookie
     */
    public static Cookie findCookie(Cookie[] cookies, String name) {
        if (cookies == null || StringUtil.isEmpty(name) || cookies.length == 0) return null;
        for (Cookie cookie : cookies) if (name.equals(cookie.getName())) return cookie;
        return null;
    }

    /**
     * modifyCookie
     * findCookie->creatCookie(创建新的Cookie进行覆盖)
     */
    public static Boolean modifyCookie(String name, String value, HttpServletRequest req, HttpServletResponse resp) {
        if (!StringUtil.isEmpty(name) && !StringUtil.isEmpty(value) && req != null && resp != null)
            return findCookie(getCookies(req), name) == null ? false : creatCookie(name, value, resp);
        return false;
    }

    /**
     * deleteCookie
     * findCookie->setMaxAge=0->resp.addCookie
     */
    public static Boolean deleteCookie(String name, HttpServletRequest req, HttpServletResponse resp) {
        if (!StringUtil.isEmpty(name) && req != null && resp != null) {
            Cookie cookie = findCookie(getCookies(req), name);
            if (cookie != null) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * setCookieTime
     * time>0  分钟
     * findCookie->setMaxAge=0->resp.addCookie
     */
    public static Boolean setCookieTime(String name, int time, HttpServletRequest req, HttpServletResponse resp) {
        if (!StringUtil.isEmpty(name) && time > 0 && req != null && resp != null) {
            Cookie cookie = findCookie(getCookies(req), name);
            if (cookie != null) {
                cookie.setMaxAge(time);
                resp.addCookie(cookie);
                return true;
            }
            return false;
        }
        return false;
    }

}
