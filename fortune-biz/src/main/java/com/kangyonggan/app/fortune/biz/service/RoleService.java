package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Role;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
public interface RoleService {

    /**
     * 查找商户角色
     *
     * @param merchCo
     * @return
     */
    List<Role> findRolesByMercoCo(String merchCo);

    /**
     * 查找所有角色
     *
     * @return
     */
    List<Role> findAllRoles();
}
