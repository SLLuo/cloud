package com.newtouch.commons.shiro;

import com.newtouch.commons.utils.EncodeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * shiro的认证授权域
 *
 * @author yuqs
 * @version 1.0
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm implements InitializingBean {
    public static final Logger LOGGER = LoggerFactory.getLogger(ShiroPrincipal.class);
    //注入用户管理对象
    private ShiroService shiroService;

    /**
     * 构造函数，设置安全的初始化信息
     */
    public ShiroAuthorizingRealm(ShiroService shiroService) {
        super();
        this.shiroService = shiroService;
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    /**
     * 获取当前认证实体的授权信息（授权包括：角色、权限）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前登录的用户名
        ShiroPrincipal principal = (ShiroPrincipal) super.getAvailablePrincipal(principals);
        if (!principal.isInit()) {
            try {
                shiroService.initRoleAndPermission(principal);
                principal.setInit(true);
            } catch (Exception e) {
                LOGGER.error(principal.getUsername() + "角色权限初始化失败", e);
                return null;
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //给当前用户设置角色
        info.setRoles(principal.getRoles());
        //给当前用户设置权限
        info.setStringPermissions(principal.getPermissions());
        return info;
    }

    /**
     * 根据认证方式（如表单）获取用户名称、密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (StringUtils.isEmpty(username)) {
            LOGGER.warn("用户名不能为空");
            throw new AccountException("用户名不能为空");
        }

        ShiroPrincipal principal = null;
        try {
            principal = shiroService.findPrincipalByUsername(username);
        } catch (UnknownAccountException e) {
            throw e;
        } catch (Exception ex) {
            LOGGER.error("获取用户失败\n", ex);
        }
        if (principal == null) {
            LOGGER.warn("用户不存在");
            throw new UnknownAccountException("用户不存在");
        }
        if (!principal.getEnabled()) {
            LOGGER.warn("用户被禁止使用");
            throw new UnknownAccountException("用户被禁止使用");
        }
        LOGGER.info("用户【" + username + "】登录成功");
        ByteSource salt = ByteSource.Util.bytes(EncodeUtils.hexDecode(principal.getSalt()));
        return new SimpleAuthenticationInfo(principal, principal.getPassword(), salt, getName());
    }

    public void afterPropertiesSet() throws Exception {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ShiroService.HASH_ALGORITHM);
        matcher.setHashIterations(ShiroService.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

}
