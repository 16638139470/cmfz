package com.baizhi.cmfzcontroller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    public Map<String, Object> login(String phone, String password, String code, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if(phone==null){
            map.put("error","手机号为空");
        }else{
            if (code == null && password != null) {
                User user = new User();
                user.setPhone(phone);
                user.setPassword(password);
                User user1 = userService.selectOne(user);
                if (user1 != null) {
                    map.put("password", user1.getPassword());
                    map.put("farmington", user1.getDharma());
                    map.put("uid", user1.getId());
                    map.put("nickname", user1.getUsername());
                    if (user1.getSex().equals("男")) {
                        map.put("gender", "m");
                    } else {
                        map.put("gender", "f");
                    }
                    map.put("photo", user1.getPhoto());
                    map.put("location", user1.getProvince() + "" + user1.getCity());
                    map.put("province", user1.getProvince());
                    map.put("city", user1.getCity());
                    map.put("description", user1.getSign());
                    map.put("phone", user1.getPhone());
                } else {
                    map.put("error", "-200");
                    map.put("errmsg", "密码错误");
                }
            }
            if (code != null && password == null) {
                String code1 = (String) session.getAttribute("code");
                if (code1.equals(code)) {
                    User user = new User();
                    user.setPhone(phone);
                    User user1 = userService.selectOne(user);
                    if (user1 != null) {
                        map.put("password", user1.getPassword());
                        map.put("farmington", user1.getDharma());
                        map.put("uid", user1.getId());
                        map.put("nickname", user1.getUsername());
                        if (user1.getSex().equals("男")) {
                            map.put("gender", "m");
                        } else {
                            map.put("gender", "f");
                        }
                        map.put("photo", user1.getPhoto());
                        map.put("location", user1.getProvince() + "" + user1.getCity());
                        map.put("province", user1.getProvince());
                        map.put("city", user1.getCity());
                        map.put("description", user1.getSign());
                        map.put("phone", user1.getPhone());
                    } else {
                        map.put("error", "-200");
                        map.put("errmsg", "密码错误");
                    }
                } else {
                    map.put("errmsg", "验证码错误");
                }

            }

        }
        return map;
    }
}
