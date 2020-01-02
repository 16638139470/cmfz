package com.baizhi;


import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Cmfz1ApplicationTests {
    @Autowired
    ArticleService articleService;
    @Test
    public void test1() {
        Map<String, Object> map = articleService.showAllArticle(1, 2);
    }

    @Test
    public void test2() {
        Article article = new Article();
        article.setId("04261c4f-5ee2-4824-bad0-69b07957dba8");
        articleService.del(article);
    }
}
