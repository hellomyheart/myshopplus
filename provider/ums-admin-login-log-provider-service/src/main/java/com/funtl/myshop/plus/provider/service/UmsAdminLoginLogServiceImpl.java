package com.funtl.myshop.plus.provider.service;

import javax.annotation.Resource;

import com.funtl.myshop.plus.provider.domain.UmsAdminLoginLog;
import com.funtl.myshop.plus.provider.mapper.UmsAdminLoginLogMapper;
import com.funtl.myshop.plus.provider.api.UmsAdminLoginLogService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
public class UmsAdminLoginLogServiceImpl implements UmsAdminLoginLogService{

    @Resource
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;

    @Override
    public int insert(UmsAdminLoginLog umsAdminLoginLog) {
        return umsAdminLoginLogMapper.insert(umsAdminLoginLog);
    }
}
