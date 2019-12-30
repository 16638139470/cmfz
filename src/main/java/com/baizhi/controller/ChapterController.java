package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.DaXiaoUtil;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ChapterController
 * @Discription
 * @Author
 * @Date 2019/12/19 14:59
 **/
@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("/showAll")
    public Map<String,Object> showAll(Integer page,Integer rows,String id){
        Map<String, Object> map = new HashMap<>();
        map = chapterService.showAllChapter(page, rows,id);
        return map;
    }
    @RequestMapping("/update")
    public Map<String,Object> edit(String oper,Chapter chapter){
        Map<String, Object> map = new HashMap<>();
        if(oper.equals("add")){
            map = add(chapter);
        }
        if(oper.equals("edit")){
            map=edit(chapter);
        }
        return map;
    }
    public Map<String,Object> add(Chapter chapter){
        Map<String, Object> map = new HashMap<>();
        String id = null;
        try {
            id = chapterService.add(chapter);
            map.put("status",true);
            map.put("message",id);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    public Map<String,Object> edit(Chapter chapter){
        Map<String, Object> map = new HashMap<>();
        //将图片路径设为null
        if (chapter.getCover().equals("")) {
            chapter.setCover(null);
        }
        try {
            String id = chapterService.edit(chapter);
            map.put("status",true);
            map.put("data",id);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("/upload")
    public void upload(MultipartFile cover, String id, HttpSession session){
        System.out.println(cover);
        //文件上传
        File file = new File(session.getServletContext().getRealPath("/audio"),cover.getOriginalFilename());
        //如果不存在，则上传
        try {
            cover.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MP3AudioHeader audioHeader=null;
        try {
            MP3File f = (MP3File) AudioFileIO.read(file);
            audioHeader = (MP3AudioHeader)f.getAudioHeader();
        } catch(Exception e) {
            e.printStackTrace();
        }
        long length = file.length();
        String formatSize = DaXiaoUtil.getFormatSize(length);
        //修改数据库中的图片路径
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setCover(cover.getOriginalFilename());
        chapter.setSize(formatSize);
        chapter.setDuration(audioHeader.getTrackLengthAsString());
        chapterService.edit(chapter);
    }
}
