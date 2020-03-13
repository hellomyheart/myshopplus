package com.funtl.myshop.plus.business.service;
import com.funtl.myshop.plus.provider.api.UmsAdminService;
import com.funtl.myshop.plus.provider.domain.UmsAdmin;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 自定义认证授权
 * <p>
 * Description:
 * </p>
 *
 * @author Lusifer
 * @version v1.0.0
 * @date 2019-07-28 17:57:14
 * @see com.funtl.myshop.plus.business.service
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //给每个用户授予USER权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority =new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);

        UmsAdmin umsAdmin = umsAdminService.get(s);
        //账号存在
        if (umsAdmin!=null){
            return new User(umsAdmin.getUsername(),umsAdmin.getPassword(),grantedAuthorities);
        }
        else
        {
            return null;
        }

    }
}