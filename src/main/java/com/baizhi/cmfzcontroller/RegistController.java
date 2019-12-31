package com.baizhi.cmfzcontroller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName RegistController
 * @Discription
 * @Author
 * @Date 2019/12/31 14:50
 **/
@RestController
@RequestMapping("account")
public class RegistController {
    @Autowired
    private UserService userService;

    @RequestMapping("regist")
    public Map<String, Object> zhuce(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        if (phone != null && password != null) {
            User user = new User();
            user.setPhone(phone);
            User user1 = userService.selectOne(user);
            if (user1 == null) {
                map.put("password", password);
                map.put("uid", UUID.randomUUID().toString());
                map.put("phone", phone);
            } else {
                map.put("errno", "-200");
                map.put("error_msg", "改手机号已经存在");
            }
        } else {
            map.put("error", "信息不能为空");
        }
        return map;
    }
}
