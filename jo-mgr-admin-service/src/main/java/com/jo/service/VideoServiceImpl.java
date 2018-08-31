package com.jo.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jo.enums.BgmOperatTypeEnum;
import com.jo.mapper.BgmMapper;
import com.jo.pojo.Bgm;
import com.jo.pojo.BgmExample;
import com.jo.utils.JsonUtils;
import com.jo.utils.PagedResult;
import com.jo.web.utils.ZKCurator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private BgmMapper bgmMapper;
    @Autowired
    private Sid sid;
    @Autowired
	private ZKCurator zkCurator;
    @Override
    public void addBgm(Bgm bgm) {
        String id = sid.nextShort();
        bgm.setId(id);
        bgmMapper.insert(bgm);
        Map<String,String> map = new HashMap<>();
        map.put("opType",BgmOperatTypeEnum.ADD.type);
        String path = bgm.getPath();
        try {
            map.put("path", new String(path.getBytes("UTF-8"),"GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        zkCurator.sendBgnOperator(id, JsonUtils.objectToJson(map));
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
        Bgm bgm = bgmMapper.selectByPrimaryKey(id);
        bgmMapper.deleteByPrimaryKey(id);
        Map<String,String> map = new HashMap<>();
        String path = bgm.getPath();
        try {
            map.put("path", new String(path.getBytes("UTF-8"),"GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("opType",BgmOperatTypeEnum.DELETE.type);
        zkCurator.sendBgnOperator(id, JsonUtils.objectToJson(map));
    }
}
