package com.clls.controller;

import com.clls.domain.Information;
import com.clls.domain.PageBean;
import com.clls.service.InformationService;
import com.clls.service.UserService;
import net.sf.json.JSONObject;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping("information")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/homepage")
    public @ResponseBody
    PageBean<Information> homepage(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);

//        Iterator<String> it = jsonObject.keys();
//        while (it.hasNext()) {
//            String key = it.next();
//            String value = jsonObject.getString(key);
//            System.out.println("key: " + key + ",value:" + value);
//        }

        String currentPage = jsonObject.getString("_currentPage");
        if ("0".equals(currentPage)) {
            return null;
        } else {
//            System.out.println("双参数");
            PageBean<Information> pb;
            pb = informationService.findByPage(currentPage);
            return pb;
        }
    }

    @RequestMapping(path = "/userhome")
    public @ResponseBody
    PageBean<Information> userhome(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
        String currentPage = jsonObject.getString("_currentPage");
        int userId = 0;
        String temp;
        try {
            temp = jsonObject.getString("userId");
            userId = Integer.parseInt(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("0".equals(currentPage)) {
            return null;
        } else {
//            System.out.println("双参数");
            PageBean<Information> pb;
            pb = informationService.findByPage(currentPage, userId, 0);
            return pb;
        }
    }

    @RequestMapping(path = "orderTaking")
    public @ResponseBody
    Boolean orderTaking(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
        String temp = jsonObject.getString("infoId");
        int infoId = Integer.parseInt(temp);
        HttpSession session = request.getSession();
        //当前用户id
        int userId;
        //发布悬赏的用户id
        int uidOfInfo;
        //接单操作
        if (session.getAttribute("userId") != null) {
            userId = (int) session.getAttribute("userId");
            uidOfInfo = informationService.findUserIdById(infoId);
            System.out.println("userID : " + userId + " UID of info : " + uidOfInfo);
            //确认进行领取操作的不是当前用户本人
            //确认该订单的状态为待领取
            if (userId != uidOfInfo && informationService.findState(infoId) == 1) {
                //设置该订单状态为2（进行中）
                informationService.modifyState(infoId, 2);
                //设置用户接单信息
                userService.receiveOrder(userId, infoId);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "deleteOrder")
    public @ResponseBody
    Boolean deleteOrder(HttpServletRequest request, @RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
        String temp = jsonObject.getString("infoId");
        int infoId = Integer.parseInt(temp);
        HttpSession session = request.getSession();

        //当前用户id
        int userId;
        //发布悬赏的用户id
        int uidOfInfo;
        if (session.getAttribute("userId") != null) {
            userId = (int) session.getAttribute("userId");
            uidOfInfo = informationService.findUserIdById(infoId);
            //确认进行删除操作的是当前用户本人
            //确认当前订单未被领取
            if (userId == uidOfInfo && informationService.findState(infoId) == 1) {
                informationService.deleteOneInformation(infoId);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "cancelOrder")
    public @ResponseBody
    Boolean cancelOrder(HttpServletRequest request, @RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
        String temp = jsonObject.getString("infoId");
        int infoId = Integer.parseInt(temp);
        HttpSession session = request.getSession();

        //当前用户id
        int userId;
        if (session.getAttribute("userId") != null) {
            userId = (int) session.getAttribute("userId");
            ////确定取消订单的是接单者本人
            //确认当前订单是进行中状态
            if (userId == userService.findServantIdByInfoId(infoId) && informationService.findState(infoId) == 2) {
                informationService.cancelAnOrderByInfoId(infoId);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "completeOrder")
    public @ResponseBody
    Boolean completeOrder(HttpServletRequest request, @RequestBody String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
        String temp = jsonObject.getString("infoId");
        int infoId = Integer.parseInt(temp);
        HttpSession session = request.getSession();

        //当前用户id
        int userId;
        //发布悬赏的用户id
        int uidOfInfo;
        if (session.getAttribute("userId") != null) {
            userId = (int) session.getAttribute("userId");
            uidOfInfo = informationService.findUserIdById(infoId);
            ////确定完成订单的是发布者本人
            //确认当前订单是进行中状态
            if (userId == uidOfInfo && informationService.findState(infoId) == 2) {
//                informationService.cancelAnOrderByInfoId(infoId);
                informationService.completeOrderByInfoId(infoId);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(path = "publish")
    public String publish(HttpServletResponse response, HttpServletRequest request,
                          Information information) {
        double bounty = information.getBounty();
        String content = information.getContent();
        //获取发布信息的用户ID
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            int userId = (int) session.getAttribute("userId");
            information.setDate(new Date());
            information.setUserId(userId);
            System.out.println(userId);
            informationService.saveInformation(information);
            return "redirect:../";
        }
        return "failure";
    }
}
