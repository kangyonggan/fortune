package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Role;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
public interface RoleService {

    /**
     * 查找用户角色
     *
     * @param username
     * @return
     */
    List<Role> findRolesByUsername(String username);
}
