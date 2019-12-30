package com.baizhi.cmfzcontroller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WenController
 * @Discription
 * @Author
 * @Date 2019/12/29 9:17
 **/
@RestController
@RequestMapping("detail")
public class WenController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("wen")
    public Map<String,Object> wen(String id,String uid){
        Map<String, Object> map = new HashMap<>();
        if(id==null||uid==null){
            map.put("error","参数不能为空");
        }else{
            Album album = new Album();
            album.setId(id);
            Chapter chapter = new Chapter();
            chapter.setAlbum_id(id);
            map.put("introduction",albumService.selectOne(album));
            map.put("list",chapterService.showAlbumAllChapter(chapter));
        }
        return map;
    }
}
