package com.baizhi.service;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName AlbumServiceImpl
 * @Discription
 * @Author
 * @Date 2019/12/19 13:30
 **/
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDAO albumDAO;

    @Override
    public Map<String, Object> showAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Album album = new Album();
        RowBounds row = new RowBounds((page-1)*rows,rows);
        List<Album> albums = albumDAO.selectByRowBounds(album, row);
        int i = albumDAO.selectCount(album);
        map.put("rows",albums);
        map.put("page",page);
        map.put("total",i%rows==0?i/rows:i/rows+1);
        map.put("records",i);
        return map;
    }

    @Override
    public String  add(Album album) {
        String id=UUID.randomUUID().toString();
        album.setId(id);
        album.setCreate_date(new Date());
        album.setCount(0);
        int i = albumDAO.insertSelective(album);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return id;
    }

    @Override
    public void edit(Album album) {
        int i = albumDAO.updateByPrimaryKeySelective(album);
        if(i==0){
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void delete(Album album) {
        int i = albumDAO.delete(album);
        if(i==0){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public List<Album> cmfzAlbumAll() {
        return albumDAO.selectAll();
    }

    @Override
    public Album selectOne(Album album) {
        return albumDAO.selectOne(album);
    }
}
