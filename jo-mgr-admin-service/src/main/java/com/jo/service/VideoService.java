package com.jo.service;

import com.jo.pojo.Bgm;
import com.jo.utils.PagedResult;

public interface VideoService {
    void addBgm(Bgm bgm);
    PagedResult queryBgmList(Integer page, Integer pageSzie);
    void deleteBgm(String id);
}
