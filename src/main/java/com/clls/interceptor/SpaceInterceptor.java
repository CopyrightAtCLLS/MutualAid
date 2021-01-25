package com.clls.interceptor;

//import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SpaceInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
//        System.out.println(request.getRequestURI());
        int id = 0;
        if (request.getRequestURI().equals("/mutualAid/space/userhome")||
        request.getRequestURI().equals("/mutualAid/space/userhomeBean")||
        request.getRequestURI().equals("/mutualAid/space/findOwner")) {
            return true;
        } else {
            try {
                id = getUserhomeId(request.getRequestURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpSession session=request.getSession();
        session.setAttribute("homeOwner", id);
        request.getRequestDispatcher("userhome").forward(request,response);
        return false;
    }

    public int getUserhomeId(String path) {
        int length = path.length();
        int indexOfSlash = 0;
        for (int i = length - 1; i >= 0; i--) {
            if (path.charAt(i) == '/') {
                indexOfSlash = i;
                break;
            }
        }
        int id=0;
        String num = path.substring(indexOfSlash + 1);
//        System.out.println("子串 : "+num);
        try {
            id = Integer.parseInt(num);
        }catch (Exception e){
//            e.printStackTrace();
        }
        return id;

    }
}
