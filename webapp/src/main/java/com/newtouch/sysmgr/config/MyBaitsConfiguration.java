package com.newtouch.sysmgr.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/19.
 */
@Configurable
@MapperScan("mybatis")
public class MyBaitsConfiguration {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.newtouch.sysmgr.model");
        factoryBean.setTypeHandlersPackage("com.newtouch.commons.mybatis.handler");
        Properties properties = new Properties();
        properties.setProperty("mapUnderscoreToCamelCase", "true");
        factoryBean.setConfigurationProperties(properties);
        PageInterceptor pageInterceptor = new PageInterceptor();
        properties = new Properties();
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "pageNum=pageNum;pageSize=pageSize;");
        pageInterceptor.setProperties(properties);
        factoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        return factoryBean;
    }

}
