package com.baizhi.service;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.deleteCache;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName ArticleServiceImpl
 * @Discription
 * @Author
 * @Date 2019/12/20 14:40
 **/
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;

    @AddCache
    @Override
    public Map<String, Object> showAllArticle(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDAO.selectByRowBounds(article, rowBounds);
        if(articles.isEmpty()){
            map.put("page",page-1);
            rowBounds = new RowBounds((page - 2) * rows, rows);
            articles = articleDAO.selectByRowBounds(article, rowBounds);
        }else{
            map.put("page",page);
        }
        int i = articleDAO.selectCount(article);
        map.put("rows",articles);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        map.put("records",i);
        return map;
    }

    @Override
    public String add(Article article) {
        String id = UUID.randomUUID().toString();
        article.setId(id);
        article.setCreate_date(new Date());
        int i = articleDAO.insertSelective(article);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return id;
    }

    @Override
    public void edit(Article article) {
        int i = articleDAO.updateByPrimaryKeySelective(article);
        if(i==0){
            throw new RuntimeException("修改失败");
        }
    }

    @deleteCache
    @Override
    public void del(Article article) {
        int i = articleDAO.delete(article);
        if(i==0){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public List<Article> cmfzShangAll(Article article) {
        return articleDAO.select(article);
    }

    @Override
    public List<Article> cmfzXianAll() {
        return articleDAO.selectAll();
    }
}
