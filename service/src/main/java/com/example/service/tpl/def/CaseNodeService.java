package com.example.service.tpl.def;

import com.example.base.pojo.CaseNode;
import com.example.base.pojo.PageParam;

public interface CaseNodeService {

    boolean createCaseNode(CaseNode caseNode);

    boolean removeCaseNode(CaseNode caseNode);

    boolean updateCaseNode(CaseNode caseNode);

    PageParam selectCaseNode(PageParam pageParam);
}
