package com.funtl.myshop.plus.business.feign;

import com.funtl.myshop.plus.configuration.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//"profile"对于service里的controller里的ProfieController中的@RequestMapping(value = "profile")s
@FeignClient(value = "business-profile", path = "profile",configuration = FeignRequestConfiguration.class)
public interface ProfileFeign {
    @GetMapping(value = "info/{username}")
    String info(@PathVariable String username);

}
