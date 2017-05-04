package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.ShiroUser;
import com.kangyonggan.app.fortune.model.vo.User;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
public interface UserService {

    /**
     * 查找用户
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    ShiroUser getShiroUser();
}
