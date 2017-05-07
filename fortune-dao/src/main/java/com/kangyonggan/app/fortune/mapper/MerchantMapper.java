package com.kangyonggan.app.fortune.mapper;

import com.kangyonggan.app.fortune.model.vo.Merchant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantMapper extends MyMapper<Merchant> {

    /**
     * 保存商户角色
     *
     * @param merchCo
     * @param roleCodes
     */
    void insertMerchantRoles(@Param("merchCo") String merchCo, @Param("roleCodes") List<String> roleCodes);
}