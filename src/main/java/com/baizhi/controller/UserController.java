package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Discription
 * @Author
 * @Date 2019/12/23 13:56
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/showAll")
    public Map<String,Object> showAll(Integer page,Integer rows){
        Map<String, Object> map = new HashMap<>();
        map = userService.showAll(page, rows);
        return map;
    }
    @RequestMapping("/update")
    public Map<String,Object> update(String oper, User user){
        Map<String, Object> map = new HashMap<>();
        if(oper.equals("edit")){
            map=edit(user);
        }
        return map;
    }
    public Map<String,Object> edit(User user){
        Map<String, Object> map = new HashMap<>();
        try {
            userService.edit(user);
            map.put("status",true);
            map.put("message","updateOk");
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("/userOut")
    public void userOut(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<User> users = userService.selectAll();
        //将路径补全
        String path = request.getSession().getServletContext().getRealPath("/image");
        for(User u:users){
            u.setPhoto(path+"//"+u.getPhoto());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试1","表1"),
                User.class, users);
        //设置响应头
        String encode= URLEncoder.encode("嘻嘻.xls","UTF-8");
        response.setHeader("content-disposition","attachment;filename="+encode);
        workbook.write(response.getOutputStream());
    }
    @RequestMapping("/showZhuce")
    public List<User> showZhuce(){
        List<User> users = userService.showZhuce();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }
}
