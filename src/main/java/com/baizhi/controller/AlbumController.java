package com.baizhi.controller;

import com.baizhi.entity.Album;

import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName AlbumController
 * @Discription
 * @Author
 * @Date 2019/12/19 13:40
 **/
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("/showAll")
    public Map<String,Object> show(Integer page, Integer rows){
        return albumService.showAll(page, rows);
    }
    @RequestMapping("/edit")
    public Map<String,Object> update(String oper,Album album){
        Map<String, Object> map = new HashMap<>();
        if(oper.equals("add")){
            map = add(album);
        }
        if(oper.equals("edit")){
            map=edit(album);
        }
        return map;
    }
    public Map<String,Object> add(Album album){
        Map<String, Object> map = new HashMap<>();
        String id = null;
        try {
            id = albumService.add(album);
            map.put("status",true);
            map.put("message",id);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    public Map<String,Object> edit(Album album){
        Map<String, Object> map = new HashMap<>();
        //将图片路径设为null
        if (album.getCover().equals("")) {
            album.setCover(null);
        }
        try {
            albumService.edit(album);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("/upload")
    public void upload(MultipartFile cover, String id, HttpSession session){
        //文件上传
        File file = new File(session.getServletContext().getRealPath("/image"),cover.getOriginalFilename());
        //如果不存在，则上传
        try {
            cover.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改数据库中的图片路径
        Album album = new Album();
        album.setId(id);
        album.setCover(cover.getOriginalFilename());
        albumService.edit(album);
    }
}

