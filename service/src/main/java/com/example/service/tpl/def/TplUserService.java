package com.example.service.tpl.def;

import com.example.base.pojo.TplUser;

public interface TplUserService {

    int insertTplUser(TplUser tplUser);

    boolean isExistUsername(String username);
}
