package com.kangyonggan.app.fortune.biz.shiro;

import com.kangyonggan.app.fortune.biz.service.MenuService;
import com.kangyonggan.app.fortune.biz.service.MerchantService;
import com.kangyonggan.app.fortune.biz.service.RoleService;
import com.kangyonggan.app.fortune.common.util.Encodes;
import com.kangyonggan.app.fortune.model.constants.AppConstants;
import com.kangyonggan.app.fortune.model.vo.Menu;
import com.kangyonggan.app.fortune.model.vo.Merchant;
import com.kangyonggan.app.fortune.model.vo.Role;
import com.kangyonggan.app.fortune.model.vo.ShiroMerchant;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author kangyonggan
 * @since 16/5/15
 */
@Log4j2
@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     * 经测试：本例中该方法的调用时机为需授权资源被访问时
     * 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroMerchant shiroMerchant = (ShiroMerchant) principalCollection.getPrimaryPrincipal();
        log.info("Shiro权限认证, merchCo={}", shiroMerchant.getMerchCo());
        List<Role> roles = roleService.findRolesByMercoCo(shiroMerchant.getMerchCo());
        List<Menu> menus = menuService.findMenusByMerchCo(shiroMerchant.getMerchCo());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 基于Role的权限信息
        for (Role role : roles) {
            info.addRole(role.getCode());
        }
        // 基于Permission的权限信息
        for (Menu menu : menus) {
            addStringPermission(info, menu);
        }

        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        log.info("Shiro登录认证, merchCo={}", token.getUsername());

        String merchCo = token.getUsername();
        Merchant merchant = merchantService.findMerchantByMerchCo(merchCo);

        if (null == merchant) {
            throw new UnknownAccountException();
        }

        if (merchant.getIsDeleted() == 1) {
            throw new DisabledAccountException();
        }

        byte[] salt = Encodes.decodeHex(merchant.getSalt());
        ShiroMerchant shiroMerchant = new ShiroMerchant();
        shiroMerchant.setId(merchant.getId());
        shiroMerchant.setMerchCo(merchant.getMerchCo());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(shiroMerchant,
                merchant.getPassword(), ByteSource.Util.bytes(salt), getName());

        return simpleAuthenticationInfo;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AppConstants.HASH_ALGORITHM);
        matcher.setHashIterations(AppConstants.HASH_INTERATIONS);

        setCredentialsMatcher(matcher);
    }

    private void addStringPermission(SimpleAuthorizationInfo info, Menu menu) {
        info.addStringPermission(menu.getCode());

        if (menu.getLeaf() != null) {
            for (Menu subMenu : menu.getLeaf()) {
                addStringPermission(info, subMenu);
            }
        }
    }
}
