package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Menu;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
public interface MenuService {

    /**
     * 查找用户菜单
     *
     * @param username
     * @return
     */
    List<Menu> findMenusByUsername(String username);
}
