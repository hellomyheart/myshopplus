package com.funtl.myshop.plus.provider.tests;

import java.util.Date;

import com.funtl.myshop.plus.provider.api.UmsAdminService;
import com.funtl.myshop.plus.provider.domain.UmsAdmin;
import com.funtl.myshop.plus.provider.mapper.UmsAdminMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
//@Transactional
//@Rollback
@SpringBootTest
public class UmsAdminTests {
    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Resource
    private UmsAdminService umsAdminService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testSelectAll() {
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectAll();
        umsAdmins.forEach(umsAdmin -> {
            System.out.println(umsAdmin);
        });
    }

    @Test
    public void testInsert() {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(120);
        umsAdmin.setUsername("testuser");
        umsAdmin.setPassword(passwordEncoder.encode("123456"));
        umsAdmin.setIcon("fg");
        umsAdmin.setEmail("fgf");
        umsAdmin.setNickName("fgf");
        umsAdmin.setNote("fgfg");
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setLoginTime(new Date());
        umsAdmin.setStatus(10);
        int result = umsAdminService.insert(umsAdmin);
//        if (result==1)
//            System.out.println(1);
//        else
//            System.out.println(0);

        Assert.assertEquals(result,1);


    }

}
