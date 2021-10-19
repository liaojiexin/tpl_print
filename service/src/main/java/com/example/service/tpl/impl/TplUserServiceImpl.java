package com.example.service.tpl.impl;

import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplUser;
import com.example.dao.mapper.TplUserMapper;
import com.example.service.tpl.def.TplUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TplUserServiceImpl implements TplUserService {

    @Resource
    private TplUserMapper tplUserMapper;

    @Override
    public int insertTplUser(TplUser tplUser) {
        return tplUserMapper.insertSelective(tplUser);
    }

    public boolean isExistUsername(String username){
        TplUser tplUser=tplUserMapper.findUserByName(username);
        if (tplUser!=null)
            return true;
        else
            return false;
    }

    @Override
    public PageParam selectAllUser(PageParam pageParam) {
        try {
            PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), true);
            List<TplUser> list = tplUserMapper.selectAllUser();
            PageInfo pageInfo = new PageInfo(list);
            pageParam.setContent(pageInfo.getList());
            pageParam.setTotal(pageInfo.getTotal());
            return pageParam;
        } finally {
            PageHelper.clearPage();
        }
    }
}
