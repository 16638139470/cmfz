package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerService {
    public List<Banner> queryAll(Integer page,Integer rows,Banner banner);
    public Integer count(Banner banner);
    public Integer totalPage(Banner banner,Integer rows);
    public void addOne(Banner banner);
    public void deleteOne(Banner banner);
    public void updateOne(Banner banner);
    public Banner selectOne(String id);
    public List<Banner> cmfzBannerAll();
}
