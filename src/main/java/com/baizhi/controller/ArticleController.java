package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ArticleController
 * @Discription
 * @Author
 * @Date 2019/12/20 14:49
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/showAll")
    public Map<String,Object> showAll(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        map = articleService.showAllArticle(page, rows);
        return map;
    }
     @RequestMapping("/update")
     public Map<String,Object> update(String oper,Article article){
         Map<String,Object> map = new HashMap<>();
         if(oper.equals("add")){
             map=add(article);
         }
         if(oper.equals("edit")){
             map=edit(article);
         }
         if(oper.equals("del")){
             map=del(article);
         }
         return map;
     }
    public Map<String,Object> edit(Article article){
        Map<String, Object> map = new HashMap<>();
        try {
            articleService.edit(article);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }

        return map;
    }
    public Map<String,Object> add(Article article){
        Map<String, Object> map = new HashMap<>();
        String id = null;
        try {
            id = articleService.add(article);
            map.put("data",id);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }

        return map;
    }
    public Map<String,Object> del(Article article){
        Map<String, Object> map = new HashMap<>();
        articleService.del(article);
        map.put("status",true);
        return map;
    }
}
