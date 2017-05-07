package com.kangyonggan.app.fortune.biz.service.impl;

import com.kangyonggan.app.fortune.biz.service.MenuService;
import com.kangyonggan.app.fortune.mapper.MenuMapper;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.vo.Menu;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/3/17
 */
@Service
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @LogTime
    public List<Menu> findMenusByMerchCo(String merchCo) {
        List<Menu> menus = menuMapper.selectMenusByMerchCo(merchCo);
        List<Menu> wrapList = new ArrayList();

        return recursionList(menus, wrapList, "", 0L);
    }

    @Override
    public List<Menu> findMenus4Role(String code) {
        return menuMapper.selectMenus4Role(code);
    }

    @Override
    @LogTime
    public boolean existsMenuCode(String code) {
        Menu menu = new Menu();
        menu.setCode(code);

        return super.exists(menu);
    }

    @Override
    public List<Menu> findAllMenus() {
        Example example = new Example(Menu.class);
        example.setOrderByClause("sort asc");

        List<Menu> menus = myMapper.selectByExample(example);
        List<Menu> wrapList = new ArrayList();

        return recursionTreeList(menus, wrapList, "", 0L);
    }

    @Override
    @LogTime
    public Menu findMenuByCode(String code) {
        Menu menu = new Menu();
        menu.setCode(code);

        return myMapper.selectOne(menu);
    }

    @Override
    @LogTime
    public void saveMenu(Menu menu) {
        myMapper.insertSelective(menu);
    }

    @Override
    @LogTime
    public Menu findMenuById(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    @LogTime
    public void updateMenu(Menu menu) {
        myMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    @LogTime
    public void deleteMenu(Menu menu) {
        myMapper.deleteByPrimaryKey(menu);
    }

    /**
     * 递归找出 parentCode 下边的所有子节点
     *
     * @param from
     * @param toList
     * @param pcode
     * @param pid
     * @return
     */
    private List<Menu> recursionList(List<Menu> from, List<Menu> toList, String pcode, Long pid) {

        if (CollectionUtils.isEmpty(from)) {
            return null;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (pcode.equals(menu.getPcode())) {
                List<Menu> leaf = new ArrayList();
                menu.setLeaf(leaf);
                menu.setPid(pid);
                toList.add(menu);
                recursionList(from, leaf, menu.getCode(), menu.getId());
            }
        }
        return toList;
    }

    /**
     * 递归找出 parentCode 下边的所有子节点
     *
     * @param from
     * @param toList
     * @param pcode
     * @param pid
     * @return
     */
    private List<Menu> recursionTreeList(List<Menu> from, List<Menu> toList, String pcode, Long pid) {

        if (CollectionUtils.isEmpty(from)) {
            return null;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (pcode.equals(menu.getPcode())) {
                menu.setPid(pid);
                toList.add(menu);
                recursionTreeList(from, toList, menu.getCode(), menu.getId());
            }
        }
        return toList;
    }
}
