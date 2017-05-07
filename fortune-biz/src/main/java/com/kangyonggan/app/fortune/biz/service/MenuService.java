package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Menu;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
public interface MenuService {

    /**
     * 查找商户菜单
     *
     * @param merchCo
     * @return
     */
    List<Menu> findMenusByMerchCo(String merchCo);

    /**
     * 查找角色菜单
     *
     * @param code
     * @return
     */
    List<Menu> findMenus4Role(String code);

    /**
     * 查找所有菜单
     *
     * @return
     */
    List<Menu> findAllMenus();
}
