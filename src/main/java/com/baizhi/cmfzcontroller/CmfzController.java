package com.baizhi.cmfzcontroller;

import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName CmfzController
 * @Discription
 * @Author
 * @Date 2019/12/27 16:52
 **/
@RestController
@RequestMapping("cmfz")
public class CmfzController {
    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @RequestMapping("first_page")
    public Map<String,Object> first_page(String uid,String type,String sub_type){
        Map<String, Object> map = new HashMap<>();
        if(uid==null||type==null){
            map.put("error","参数不能为空");
        }else{
            if(type.equals("all")){
                map.put("header",bannerService.cmfzBannerAll());
            }
            if(type.equals("wen")){
                map.put("body",albumService.cmfzAlbumAll());
            }
            if(type.equals("si")){
                if(sub_type==null){
                    map.put("error","参数不能为空");
                }else{
                    if(sub_type.equals("ssyj")){
                        User user = new User();
                        user.setId(uid);
                        User user1 = userService.selectOne(user);
                        Article article = new Article();
                        article.setAuthor(user1.getGuruname());
                        map.put("body",articleService.cmfzShangAll(article));
                    }
                    if(sub_type.equals("xmfy")){
                        map.put("body",articleService.cmfzXianAll());
                    }
                }
            }
        }
        return map;
    }

}
