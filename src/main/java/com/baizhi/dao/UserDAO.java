package com.baizhi.dao;

import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDAO extends Mapper<User> {
    public List<User> selectZhuce();
}
