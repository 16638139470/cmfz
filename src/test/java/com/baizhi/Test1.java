package com.baizhi;

import com.baizhi.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName Test1
 * @Discription
 * @Author
 * @Date 2019/12/31 14:31
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {
    @Autowired
    UserDAO userService;
    @Test
    public void test1() {
        System.out.println("嘻嘻");
        System.out.println("你是沙雕吧");
        System.out.println("我就是沙雕");
    }

    @Test
    public void test3() {
    }
}
