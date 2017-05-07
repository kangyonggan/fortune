package com.kangyonggan.app.fortune.mapper;

import com.kangyonggan.app.fortune.model.vo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends MyMapper<Menu> {

    /**
     * 查找商户菜单
     *
     * @param merchCo
     * @return
     */
    List<Menu> selectMenusByMerchCo(@Param("merchCo") String merchCo);

}