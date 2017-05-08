package com.newtouch.sysmgr.web;

import com.newtouch.commons.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/31.
 */
@Controller
public class MainController {

    public static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Object login(String username, String password) throws Exception {
        Subject currentUser = ShiroUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            currentUser.login(token);
            Map<String, Object> result = new HashMap<>();
            result.put("message", "登录成功");
            LOGGER.debug("username:" + username + ",password:" + password + ":登录成功");
            return result;
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("message", "请不要重复登录");
            LOGGER.debug("username:" + username + ",password:" + password + ":重复登录");
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public Object logout() {
        Subject currentUser = ShiroUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
            Map<String, Object> result = new HashMap<>();
            result.put("message", "注销成功");
            LOGGER.debug("username:" + ShiroUtils.getUsername() + ":注销成功");
            return result;
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("message", "未登录");
            //LOGGER.debug("username:" + username + ",password:" + password + ":重复登录");
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/unauthorized")
    public Object unauthorized() {
        return null;
    }

}
