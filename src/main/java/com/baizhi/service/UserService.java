package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserService
 * @Discription
 * @Author
 * @Date 2019/12/23 13:39
 **/
public interface UserService {
    public Map<String,Object> showAll(Integer page,Integer rows);
    public String edit(User user);
    public List<User> selectAll();
    public List<User> showZhuce();
    public User selectOne(User user);

}
