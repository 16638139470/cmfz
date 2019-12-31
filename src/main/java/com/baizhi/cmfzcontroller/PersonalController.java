package com.baizhi.cmfzcontroller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PersonalController
 * @Discription
 * @Author
 * @Date 2019/12/31 15:41
 **/
@RestController
@RequestMapping("account")
public class PersonalController {
    @Autowired
    private UserService userService;

    @RequestMapping("modify")
    public Map<String, Object> updatePersonal(User user) {
        Map<String, Object> map = new HashMap<>();
        if (user.getId() != null) {
            User user1 = userService.selectOne(user);
            if (user1 == null) {
                map.put("password", user.getPassword());
                map.put("farmington", user.getDharma());
                map.put("uid", user.getId());
                map.put("nickname", user.getUsername());
                if (user.getSex().equals("男")) {
                    map.put("gender", "m");
                } else {
                    map.put("gender", "f");
                }
                map.put("photo", user.getPhoto());
                map.put("location", user.getProvince() + "" + user.getCity());
                map.put("province", user.getProvince());
                map.put("city", user.getCity());
                map.put("description", user.getSign());
                map.put("phone", user.getPhone());
            } else {
                map.put("errno", "-200");
                map.put("error_msg", "该手机号已经存在");
            }

        }
        return map;
    }
}
