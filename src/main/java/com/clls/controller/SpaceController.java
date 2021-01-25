package com.clls.controller;

import com.clls.domain.Information;
import com.clls.domain.PageBean;
import com.clls.domain.User;
import com.clls.service.InformationService;
import com.clls.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("space")
public class SpaceController {
    @Autowired
    InformationService informationService;
    @Autowired
    UserService userService;

    @RequestMapping(path = "userhome")
    public String userhome(HttpServletRequest request) {
        return "space";
    }

    /**
     *
     * @param request
     * @param body  封装了当前页码,页面路由标识符
     * @return
     */
    @RequestMapping(path = "userhomeBean")
    public @ResponseBody
    PageBean<Information> userhomeBean(HttpServletRequest request, @RequestBody String body) {
        HttpSession session = request.getSession();
        Object id = session.getAttribute("userId");
        //当前登录用户的ID
        int currentUserId = 0;
        if (id != null) {
            currentUserId = (int) id;
        }
        JSONObject jsonObject = JSONObject.fromObject(body);
        String currentPage = jsonObject.getString("_currentPage");
        int identifier=jsonObject.getInt("identifier");
//        System.out.println(identifier);
        //用户主页的主人ID
        int userId=0;
        if (session.getAttribute("homeOwner") != null) {
            userId = (int) session.getAttribute("homeOwner");
        }
        PageBean<Information> pb;
        pb = informationService.findByPage(currentPage, userId,identifier);
//        pb = informationService.findByPage(currentPage);
        return pb;
    }

    @RequestMapping(path="findOwner")
    public @ResponseBody User findOwnername(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=new User();
        int homeOwnerId=0;
        if(session.getAttribute("homeOwner")!=null)
            homeOwnerId= (int) session.getAttribute("homeOwner");
        String username=userService.findOneByUserId(homeOwnerId).getUsername();
        user.setUsername(username);
        user.setId(homeOwnerId);
        return user;
    }
}
