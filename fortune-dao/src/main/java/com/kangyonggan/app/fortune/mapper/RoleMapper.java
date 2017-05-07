package com.kangyonggan.app.fortune.mapper;

import com.kangyonggan.app.fortune.model.vo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends MyMapper<Role> {

    /**
     * 查找商户角色
     *
     * @param merchCo
     * @return
     */
    List<Role> selectRolesByMerchCo(@Param("merchCo") String merchCo);

    /**
     * 删除商户全部角色
     *
     * @param merchCo
     */
    void deleteAllRolesByMerchCo(@Param("merchCo") String merchCo);

    /**
     * 删除角色菜单
     *
     * @param code
     */
    void deleteRoleMenus(@Param("code") String code);

    /**
     * 插入角色菜单
     *
     * @param code
     * @param menuCodes
     */
    void insertRoleMenus(@Param("code") String code, @Param("menuCodes") List<String> menuCodes);
}