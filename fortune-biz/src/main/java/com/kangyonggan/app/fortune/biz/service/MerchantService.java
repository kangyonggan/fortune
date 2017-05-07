package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
public interface MerchantService {

    /**
     * 查找商户信息
     *
     * @param merchCo 商户号
     * @return
     */
    Merchant findMerchantByMerchCo(String merchCo);

    /**
     * 获取登录的商户信息
     *
     * @return
     */
    ShiroMerchant getShiroMerchant();

    /**
     * 搜索商户信息
     *
     * @param pageNum
     * @param merchCo
     * @param merchNm
     * @return
     */
    List<Merchant> searchMerchants(int pageNum, String merchCo, String merchNm);

    /**
     * 保存商户信息
     *
     * @param merchant
     */
    void saveMerchantWithDefaultRole(Merchant merchant);

    /**
     * 根据商户号更新商户信息
     *
     * @param merchant
     */
    void updateMerchantByMerchCo(Merchant merchant);

    /**
     * 根据商户号修改密码
     *
     * @param merchant
     */
    void updateMerchantPassword(Merchant merchant);

    /**
     * 更新商户角色
     *
     * @param merchCo
     * @param roleCodes
     */
    void updateMerchanrRoles(String merchCo, String roleCodes);

    /**
     * 查找商户，包括已删除的
     *
     * @param id
     * @return
     */
    Merchant findMerchantById(Long id);

    /**
     * 判断商户号是否存在
     *
     * @param merchCo
     * @return
     */
    boolean existsMerchCo(String merchCo);
}
