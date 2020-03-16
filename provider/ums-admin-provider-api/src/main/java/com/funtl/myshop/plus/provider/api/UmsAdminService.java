package com.funtl.myshop.plus.provider.api;

import com.funtl.myshop.plus.provider.domain.UmsAdmin;

/**
 * 用户管理服务
 */
public interface UmsAdminService {
    /**
     * 新增用户
     *
     * @param umsAdmin {@link UmsAdmin}
     * @return 大于0则表示添加成功
     */
    int insert(UmsAdmin umsAdmin);

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return
     */
    UmsAdmin get(String username);

    /**
     * 获取用户
     *
     * @param umsAdmin {@link UmsAdmin}
     * @return {@link UmsAdmin}
     */
    UmsAdmin get(UmsAdmin umsAdmin);

    /**
     * 更新用户
     * <p>
     * 仅允许更新 邮箱、昵称、备注、状态
     * </p>
     *
     * @param umsAdmin {@link UmsAdmin}
     * @return {@code int} 大于 0 则表示更新成功
     */
    int update(UmsAdmin umsAdmin);

    /**
     * 修改头像
     *
     * @param username {@code String} 用户名
     * @param path     {@code String} 头像地址
     * @return {@code int} 大于 0 则表示更新成功
     */
    int modifyIcon(String username, String path);
}