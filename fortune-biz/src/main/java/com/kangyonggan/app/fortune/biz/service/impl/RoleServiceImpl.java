package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.RoleService;
import com.kangyonggan.app.fortune.mapper.RoleMapper;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @LogTime
    public List<Role> findRolesByMercoCo(String merchCo) {
        return roleMapper.selectRolesByMerchCo(merchCo);
    }

    @Override
    @LogTime
    public List<Role> findAllRoles() {
        Role role = new Role();
        role.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.select(role);
    }
}
