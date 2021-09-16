package com.example.service.tpl.def;

import com.example.base.pojo.CaseNode;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface CaseTplService {

    byte[] caseToPdfByTpl(CaseNode caseNode);

    void mergeCasePdf(List<byte[]> list, HttpServletResponse response);
}
