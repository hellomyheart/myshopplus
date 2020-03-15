package com.funtl.myshop.plus.business.tests;

import com.funtl.myshop.plus.business.dto.ProfileParam;
import com.funtl.myshop.plus.commons.utils.MapperUtils;
import org.junit.Test;

import java.sql.SQLOutput;

public class PrintJsonTests {
    @Test
    public void testProfileParam() throws Exception {
        System.out.println(MapperUtils.obj2json(new ProfileParam()));
    }
}
