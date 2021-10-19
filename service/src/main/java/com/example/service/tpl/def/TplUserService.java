package com.example.service.tpl.def;

import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplUser;

import java.util.List;

public interface TplUserService {

    int insertTplUser(TplUser tplUser);

    boolean isExistUsername(String username);

    PageParam selectAllUser(PageParam pageParam);
}
