package com.kangyonggan.app.fortune.biz.service;

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
}
