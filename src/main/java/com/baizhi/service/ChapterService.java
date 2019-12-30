package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    public Map<String,Object> showAllChapter(Integer page,Integer rows,String id);
    public String add(Chapter chapter);
    public String edit(Chapter chapter);
    public List<Chapter> showAlbumAllChapter(Chapter chapter);
}
