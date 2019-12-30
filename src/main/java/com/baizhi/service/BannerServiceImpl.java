package com.baizhi.service;

import com.baizhi.dao.BannerDAO;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName BannerServiceImpl
 * @Discription
 * @Author
 * @Date 2019/12/18 13:14
 **/
@Service
@Transactional
public class BannerServiceImpl implements BannerService{
    @Autowired
    private BannerDAO bannerDAO;

    @Override
    public List<Banner> queryAll(Integer page,Integer rows,Banner banner) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        return bannerDAO.selectByRowBounds(banner, rowBounds);
    }

    @Override
    public Integer count(Banner banner) {
        return bannerDAO.selectCount(banner);
    }

    @Override
    public Integer totalPage(Banner banner,Integer rows) {
        Integer totalSize=bannerDAO.selectCount(banner);
        Integer totalPage=totalSize%rows==0 ? totalSize/rows:totalSize/rows+1;
        return totalPage;
    }

    @Override
    public void addOne(Banner banner) {
        bannerDAO.insert(banner);
    }

    @Override
    public void deleteOne(Banner banner) {
        bannerDAO.delete(banner);
    }

    @Override
    public void updateOne(Banner banner) {
        bannerDAO.updateByPrimaryKeySelective(banner);
    }

    @Override
    public Banner selectOne(String id) {
        return bannerDAO.selectByPrimaryKey(id);
    }

    @Override
    public List<Banner> cmfzBannerAll() {
        return bannerDAO.selectAll();
    }
}
