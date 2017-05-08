package com.newtouch.sysmgr.config;

import com.newtouch.sysmgr.dao.PermissionDAO;
import com.newtouch.sysmgr.dao.RoleDAO;
import com.newtouch.sysmgr.dao.UserDAO;
import com.newtouch.sysmgr.service.ShiroServiceImpl;
import com.newtouch.commons.shiro.ShiroAuthorizingRealm;
import com.newtouch.commons.shiro.ShiroDefinitionSectionMetaSource;
import com.newtouch.commons.shiro.ShiroManager;
import com.newtouch.commons.shiro.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Created by Administrator on 2017/4/19.
 */
@Configurable
@MapperScan("mybatis")
public class ShiroConfiguration {


    @Bean
    public ShiroService shiroService(UserDAO userDAO, RoleDAO roleDAO, PermissionDAO permissionDAO) {
        return new ShiroServiceImpl(userDAO, roleDAO, permissionDAO);
    }

    @Bean
    public Realm realm(ShiroService shiroService) {
        return new ShiroAuthorizingRealm(shiroService);
    }

    @Bean
    public WebSecurityManager webSecurityManager(Realm realm) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(webSecurityManager);
        return webSecurityManager;
    }

    @Bean
    public ShiroDefinitionSectionMetaSource definitionSectionMetaSource(ShiroService shiroService) {
        ShiroDefinitionSectionMetaSource definitionSectionMetaSource = new ShiroDefinitionSectionMetaSource();
        definitionSectionMetaSource.setShiroService(shiroService);
        return definitionSectionMetaSource;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(WebSecurityManager securityManager,
                                              Ini.Section shiroDefinitionSectionMetaSource) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("index");
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setFilterChainDefinitionMap(shiroDefinitionSectionMetaSource);
        return shiroFilter;
    }

    @Bean
    public ShiroManager shiroManager(ShiroFilterFactoryBean shiroFilterFactoryBean,
                                     ShiroDefinitionSectionMetaSource shiroDefinitionSectionMetaSource) {
        return new ShiroManager(shiroFilterFactoryBean, shiroDefinitionSectionMetaSource);
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        // 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
        return filterRegistration;
    }

}
