package com.newtouch.commons.shiro;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * 授权元数据根据两部分构成：
 * 1、数据库中动态生成，由注入的resourceManager根据资源、权限构造资源-权限的键值对
 * 2、使用spring的注入filterChainDefinitions，在配置文件中定义默认的安全定义，如登录页面，首页等
 * @author yuqs
 * @version 1.0
 */
public class ShiroDefinitionSectionMetaSource implements FactoryBean<Ini.Section> { //
	public static final Logger LOGGER = LoggerFactory.getLogger(ShiroPrincipal.class);
	//格式化数据，符合shiro的格式，如：perms[admin]
	public static final String PREMISSION_FORMAT = "perms[%s]";
	//注入资源管理对象
	private ShiroService shiroService;

	public void setShiroService(ShiroService shiroService) {
		this.shiroService = shiroService;
	}
	
	@Override
	public Section getObject() throws Exception {
		Ini ini = new Ini();
		ini.loadFromPath("classpath:spring/shiro.properties");
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		//由注入的资源管理对象获取所有资源数据，并且Resource的authorities的属性是EAGER的fetch类型
		Map<String, Set<String>> resourcePermissionsMap = shiroService.getAllResourcePermissions();
		for(Map.Entry<String, Set<String>> entry : resourcePermissionsMap.entrySet()) {
			if(StringUtils.isEmpty(entry.getKey())) {
				continue;
			}
			for (String permission : entry.getValue()) {
				putDefinitionSection(section, entry.getKey(), permission);
			}
		}
		return section;
	}
	
	private void putDefinitionSection(Section section, String key, String value) {
		LOGGER.debug("加载数据库权限：【key=" + key + "/tvalue=" + value + "】");
	    section.put(key, String.format(PREMISSION_FORMAT, value));
	}
	
	@Override
	public Class<?> getObjectType() {
		return Ini.Section.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}


}
