package com.kangyonggan.app.fortune.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.common.util.Digests;
import com.kangyonggan.app.fortune.common.util.Encodes;
import com.kangyonggan.app.fortune.common.util.StringUtil;
import com.kangyonggan.app.fortune.mapper.MerchantMapper;
import com.kangyonggan.app.fortune.mapper.RoleMapper;
import com.kangyonggan.app.fortune.model.annotation.CacheDelete;
import com.kangyonggan.app.fortune.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.fortune.model.annotation.LogTime;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
@Service
public class MerchantServiceImpl extends BaseService<Merchant> implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @LogTime
    @CacheGetOrSave("merchant:merchCo:{0}")
    public Merchant findMerchantByMerchCo(String merchCo) {
        Merchant merchant = new Merchant();
        merchant.setMerchCo(merchCo);
        merchant.setIsDeleted(AppConstants.IS_DELETED_NO);

        return myMapper.selectOne(merchant);
    }

    @Override
    @LogTime
    public ShiroMerchant getShiroMerchant() {
        return (ShiroMerchant) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    @LogTime
    public List<Merchant> searchMerchants(int pageNum, String merchCo, String merchNm) {
        Example example = new Example(Merchant.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(merchCo)) {
            criteria.andLike("merchCo", StringUtil.toLikeString(merchCo));
        }

        if (StringUtils.isNotEmpty(merchNm)) {
            criteria.andLike("merchNm", StringUtil.toLikeString(merchNm));
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return myMapper.selectByExample(example);
    }

    @Override
    @LogTime
    public void saveMerchantWithDefaultRole(Merchant merchant) {
        entryptPassword(merchant);

        if (StringUtils.isEmpty(merchant.getCharset())) {
            merchant.setCharset(AppConstants.CHARSET);
        }
        myMapper.insertSelective(merchant);

        saveMerchantRoles(merchant.getMerchCo(), AppConstants.DEFAULT_ROLE_CODE);
    }

    @Override
    @LogTime
    @CacheDelete("merchant:merchCo:{0:merchCo}")
    public void updateMerchantByMerchCo(Merchant merchant) {
        if (StringUtils.isEmpty(merchant.getCharset())) {
            merchant.setCharset(AppConstants.CHARSET);
        }

        Example example = new Example(Merchant.class);
        example.createCriteria().andEqualTo("merchCo", merchant.getMerchCo());

        myMapper.updateByExampleSelective(merchant, example);
    }

    @Override
    @LogTime
    public void updateMerchantPassword(Merchant merchant) {
        entryptPassword(merchant);
        updateMerchantByMerchCo(merchant);
    }

    @Override
    @LogTime
    public void updateMerchanrRoles(String merchCo, String roleCodes) {
        roleMapper.deleteAllRolesByMerchCo(merchCo);

        if (StringUtils.isNotEmpty(roleCodes)) {
            saveMerchantRoles(merchCo, roleCodes);
        }
    }

    @Override
    public Merchant findMerchantById(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean existsMerchCo(String merchCo) {
        Merchant merchant = new Merchant();
        merchant.setMerchCo(merchCo);

        return super.exists(merchant);
    }

    /**
     * 批量保存商户角色
     *
     * @param merchCo
     * @param roleCodes
     */
    private void saveMerchantRoles(String merchCo, String roleCodes) {
        merchantMapper.insertMerchantRoles(merchCo, Arrays.asList(roleCodes.split(",")));
    }

    /**
     * 设定安全的密码，生成随机的salt并经过N次 sha-1 hash
     *
     * @param merchant
     */
    private void entryptPassword(Merchant merchant) {
        byte[] salt = Digests.generateSalt(AppConstants.SALT_SIZE);
        merchant.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(merchant.getPassword().getBytes(), salt, AppConstants.HASH_INTERATIONS);
        merchant.setPassword(Encodes.encodeHex(hashPassword));
    }
}
