package com.baizhi.service;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Discription
 * @Author
 * @Date 2019/12/23 13:40
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Override
    public Map<String, Object> showAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDAO.selectByRowBounds(user, rowBounds);
        int i = userDAO.selectCount(user);
        map.put("page",page);
        map.put("rows",users);
        map.put("total",i%rows==0? i/rows:i/rows+1);
        map.put("records",i);
        return map;
    }

    @Override
    public String edit(User user) {
        int i = userDAO.updateByPrimaryKeySelective(user);
        if(i==0){
            throw new RuntimeException("修改失败");
        }
        return user.getId();
    }

    @Override
    public List<User> selectAll() {
        return userDAO.selectAll();
    }

    @Override
    public List<User> showZhuce() {
        return userDAO.selectZhuce();
    }

    @Override
    public User selectOne(User user) {
        return userDAO.selectOne(user);
    }
}
