package com.clls.controller;

import com.clls.domain.User;
import com.clls.service.UserService;
import com.clls.utils.regex;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户web
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(path="checkLogin")
    public @ResponseBody Boolean checkLogin(@RequestBody String body){
        JSONObject jsonObject=JSONObject.fromObject(body);
//        System.out.println(body);
//
//        Iterator<String> it = jsonObject.keys();
//        while (it.hasNext()) {
//            String key = it.next();
//            String value = jsonObject.getString(key);
//            System.out.println("key: " + key + ",value:" + value);
//    }

        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        if(username==null){
            return false;
        }
        else if(username.length()<1){
            return false;
        }
        else if(userService.findOneByUsername(username)==null){
            return false;
        }
        if(password==null){
            return false;
        }
        else if(password.length()<1){
            return false;
        }
        else if(!password.equals(userService.findOneByUsername(username).getPassword())){
            return false;
        }
        return true;
    }

    /**
     * 用户注册控制器
     *
     * @param user
     * @return
     */
    @RequestMapping(path = "/login")
    public String login(User user, @RequestParam(required = false) String Memory,
                        HttpServletResponse response, HttpServletRequest request) {
        //密码正确
        if (user.getPassword().equals(userService.findOneByUsername(user.getUsername()).getPassword())) {
            int id = userService.findOneByUsername(user.getUsername()).getId();
            user.setId(id);
            //记住密码
            if ("on".equals(Memory)) {
                try {
                    saveCookies(user, request, response);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //不记住密码
            else {
                clearCookies(request, response);
            }
            initSession(user, request, response);
            return "redirect:../index.html";
        }
        //密码错误
        else {
            return "redirect:register.html";
        }
    }

    @RequestMapping(path = "/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.removeAttribute("userId");
    }

    @RequestMapping(path = "register")
    public String register(User user, @RequestParam(required = false) String Memory,
                           HttpServletResponse response, HttpServletRequest request) throws IOException {
//        String str="..s,ldsal.ww";
        //正则表达式处理用户名和密码
        Pattern ChineseLanguage=Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher matcher=ChineseLanguage.matcher(str);
//        System.out.println(matcher.find());
        if(user.getPassword().length()<6||ChineseLanguage.matcher(user.getPassword()).find()){
            System.out.println("regex");
            return "redirect:../";
        }
        System.out.println("注册操作");
        //记住密码
        if ("on".equals(Memory)) {
            userService.saveUser(user);
            int id = userService.findOneByUsername(user.getUsername()).getId();
            user.setId(id);
            try {
                saveCookies(user, request, response);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //不记住密码
        else {
            userService.saveUser(user);
            clearCookies(request, response);
        }
        initSession(user, request, response);
        return "redirect:../index.html";

    }

    @RequestMapping(path = "/getCookies")
    public @ResponseBody
    User getCookies(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        String username = null;
        String password = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                } else if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        }
        if (username != null && password != null) {
            user.setId(userService.findOneByUsername(username).getId());
            user.setUsername(username);
            user.setPassword(password);
        }
        return user;
    }

    @RequestMapping(path = "/getSession")
    public @ResponseBody
    User getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        Object password = session.getAttribute("password");
        Object id = session.getAttribute("userId");
        User user = new User();
        if (username != null && password != null && id != null) {
            user.setUsername((String) username);
            user.setPassword((String) password);
            user.setId((Integer) id);
        }
        return user;
    }

    @RequestMapping(path = "checkRegisterPassword")
    public @ResponseBody
    Boolean checkRegisterPassword(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
//        Iterator<String> it = jsonObject.keys();
//        while (it.hasNext()) {
//            String key = it.next();
//            String value = jsonObject.getString(key);
//            System.out.println("key: " + key + ",value:" + value);
//    }
        String password = jsonObject.getString("password");
        String secondPassword = jsonObject.getString("secondPassword");
        return password.equals(secondPassword);
    }

    @RequestMapping(path = "isUsernameExist")
    public @ResponseBody
    Boolean isUsernameExist(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
        String username = jsonObject.getString("username");
        if(username.length()<1)
            return false;
        if (userService.findOneByUsername(username) != null)
            return true;
        return false;
    }

    @RequestMapping(path="getUsernameById")
    public @ResponseBody String getUsernameById(@RequestBody String body){
        String username=null;

        return username;
    }

    private void initSession(User user, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("username", user.getUsername());
        session.setAttribute("password", user.getPassword());
        session.setAttribute("userId", user.getId());
    }

    private void saveCookies(User user, HttpServletRequest request, HttpServletResponse response) throws
            UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //username Cookie
        Cookie username = new Cookie("username", user.getUsername());
        username.setPath(request.getContextPath());
        username.setMaxAge(60 * 60 * 24 * 7);
        //password Cookie
        Cookie password = new Cookie("password", user.getPassword());
        password.setPath(request.getContextPath());
        password.setMaxAge(60 * 60 * 24 * 7);
        //id Cookie
        Cookie id = new Cookie("id", Integer.toString(user.getId()));
        id.setPath(request.getContextPath());
        id.setMaxAge(60 * 60 * 24 * 7);

        response.addCookie(username);
        response.addCookie(password);
        response.addCookie(id);
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                } else {
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }
            }
        }
    }

}
