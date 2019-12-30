package com.baizhi.service;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName ChapterServiceImpl
 * @Discription
 * @Author
 * @Date 2019/12/19 14:52
 **/
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService{
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private AlbumDAO albumDAO;
    @Override
    public Map<String, Object> showAllChapter(Integer page, Integer rows,String id) {
        Map<String, Object> map = new HashMap<>();
        Chapter chapter = new Chapter();
        chapter.setAlbum_id(id);
        RowBounds rowBounds = new RowBounds((page-1) * rows, rows);
        int i = chapterDAO.selectCount(chapter);
        List<Chapter> chapters = chapterDAO.selectByRowBounds(chapter, rowBounds);
        map.put("rows",chapters);
        map.put("page",page);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        map.put("records",i);
        return map;
    }

    @Override
    public String add(Chapter chapter) {
        String id = UUID.randomUUID().toString();
        chapter.setId(id);
        chapter.setCreate_date(new Date());
        Album album = new Album();
        album.setId(chapter.getAlbum_id());
        Album album1 = albumDAO.selectOne(album);
        album.setCount(album1.getCount()+1);
        albumDAO.updateByPrimaryKeySelective(album);
        int i = chapterDAO.insertSelective(chapter);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return id;
    }

    @Override
    public String edit(Chapter chapter) {
        int i = chapterDAO.updateByPrimaryKeySelective(chapter);
        if(i==0){
            throw new RuntimeException("修改失败");
        }
        return chapter.getId();
    }

    @Override
    public List<Chapter> showAlbumAllChapter(Chapter chapter) {
        return chapterDAO.select(chapter);
    }
}
