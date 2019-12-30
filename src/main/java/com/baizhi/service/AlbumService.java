package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    public Map<String,Object> showAll(Integer page,Integer rows);
    public String add(Album album);
    public void edit(Album album);
    public void delete(Album album);
    public List<Album> cmfzAlbumAll();
    public Album selectOne(Album album);
}
