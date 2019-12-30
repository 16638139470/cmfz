package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName AdminController
 * @Discription
 * @Author
 * @Date 2019/12/17 14:51
 **/
@Controller
@RequestMapping("/a")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/login")
    @ResponseBody
    public String loginmethod(Admin admin, HttpSession session,String enCode){
        Admin login = adminService.login(admin);
        String code = (String)session.getAttribute("code");
        if(code.equals(enCode)){
            if (login!=null){
                session.setAttribute("loginadmin",login);
                return "success";
            }else{
                return "账号或密码错误";
            }
        }
        return "验证码错误!";
    }
    @RequestMapping("/getCode")
    public String getCode(HttpSession session, HttpServletResponse response){
        String code = SecurityCode.getSecurityCode();
        session.setAttribute("code", code);
        BufferedImage img = SecurityImage.createImage(code);
        OutputStream outputStream=null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(img, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/exit")
    public String exitAdmin(HttpSession session){
        session.removeAttribute("loginadmin");
        return "redirect:/login.jsp";
    }
}
