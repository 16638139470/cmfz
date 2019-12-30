package com.baizhi.cmfzcontroller;

import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName loginController
 * @Discription
 * @Author
 * @Date 2019/12/29 9:37
 **/
@RestController
@RequestMapping("account")
public class loginController {
    @Autowired
    private UserService userService;
    @RequestMapping("login")
    public Map<String,Object> login(String phone,String password,String code){
        Map<String, Object> map = new HashMap<>();
        if(phone==null){
            map.put("error","手机号为空");
        }else{
            if(password!=null){
                map.put("success","haoba");
            }
        }
        return map;
    }
}
