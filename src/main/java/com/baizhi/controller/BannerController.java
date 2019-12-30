package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @ClassName BannerController
 * @Discription
 * @Author
 * @Date 2019/12/18 14:00
 **/
@Controller
@RequestMapping("/b")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("/showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page , Integer rows,Banner banner){
        HashMap<String, Object> map = new HashMap<>();
        List<Banner> banners = bannerService.queryAll(page, rows, banner);
        map.put("rows",banners);
        map.put("page",page);
        map.put("total",bannerService.totalPage(banner,rows));
        map.put("records",bannerService.count(banner));
        return map;
    }
    @RequestMapping("/updateBanner")
    @ResponseBody
    public Map<String,Object> updateBanner(Integer page , Integer rows,String oper,Banner banner,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if(oper.equals("edit")){
            //将图片路径设为null
            if (banner.getCover().equals("")) {
                banner.setCover(null);
            }
            bannerService.updateOne(banner);
            map.put("status","updateOk");
            map.put("data",banner.getId());
        }else if (oper.equals("add")){
            banner.setId(UUID.randomUUID().toString());
            banner.setCreate_date(new Date());
            banner.setName(banner.getName());
            banner.setDescription(banner.getDescription());
            banner.setStatus(banner.getStatus());
            bannerService.addOne(banner);
            map.put("status","addOk");
            map.put("data",banner.getId());
        }else if (oper.equals("del")){
            Banner banner1 = bannerService.selectOne(banner.getId());
            if (banner1.getCover()!=null){
                File file = new File(session.getServletContext().getRealPath("/image"), banner1.getCover());
                file.delete();
            }
            bannerService.deleteOne(banner);
/*            List<Banner> banners = bannerService.queryAll(page, rows, banner);
            map.put("rows",banners);
            map.put("page",page);
            map.put("total",bannerService.totalPage(banner,rows));
            map.put("records",bannerService.count(banner));
            map.put("status","delOk");*/
        }
        return map;
    }
    @RequestMapping("/upload")
    public void upload(MultipartFile cover,String id,HttpSession session)throws Exception{
        //文件上传
        File file = new File(session.getServletContext().getRealPath("/image"), cover.getOriginalFilename());
        //如果不存在，则上传
        if (!file.exists()) {
            cover.transferTo(file);
        }
        //修改数据库中的图片路径
        Banner banner = new Banner();
        banner.setId(id);
        banner.setCover(cover.getOriginalFilename());
        bannerService.updateOne(banner);
    }

}
