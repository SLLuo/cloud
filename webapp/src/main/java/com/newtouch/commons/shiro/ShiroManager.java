package com.newtouch.commons.shiro;

import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/31.
 */
public class ShiroManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(ShiroManager.class);

    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    private ShiroDefinitionSectionMetaSource shiroDefinitionSectionMetaSource;

    public ShiroManager(ShiroFilterFactoryBean shiroFilterFactoryBean, ShiroDefinitionSectionMetaSource shiroDefinitionSectionMetaSource) {
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
        this.shiroDefinitionSectionMetaSource = shiroDefinitionSectionMetaSource;
    }

    public void refresh() {
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        // 获取过滤管理器
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                .getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
        // 清空初始权限配置
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        try {
            Ini.Section section = shiroDefinitionSectionMetaSource.getObject();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(section);
            for (Map.Entry<String, String> entry : section.entrySet()) {
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(entry.getKey(), chainDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
