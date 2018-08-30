package com.jo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jo.mapper.BgmMapper;
import com.jo.pojo.Bgm;
import com.jo.pojo.BgmExample;
import com.jo.utils.PagedResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private BgmMapper bgmMapper;
    @Autowired
    private Sid sid;
    @Override
    public void addBgm(Bgm bgm) {
        String id = sid.nextShort();
        bgm.setId(id);
        bgmMapper.insert(bgm);
    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSzie) {

        PageHelper.startPage(page,pageSzie);
        BgmExample bgmExample = new BgmExample();
        List<Bgm> list = bgmMapper.selectByExample(bgmExample);
        PageInfo<Bgm> pageList = new PageInfo<>(list);
        PagedResult result = new PagedResult();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());
        return result;
    }

    @Override
    public void deleteBgm(String id) {
        bgmMapper.deleteByPrimaryKey(id);
    }
}
