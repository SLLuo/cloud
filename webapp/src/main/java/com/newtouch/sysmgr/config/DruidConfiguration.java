package com.newtouch.sysmgr.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
@Configurable
@PropertySource("config/druid.properties")
public class DruidConfiguration {

    @Value("${url}")
    private String url;
    @Value("${username}")
    private String username;
    @Value("${passwrod}")
    private String passwrod;
    @Value("${initialSize}")
    private Integer initialSize;
    @Value("${minIdle}")
    private Integer minIdle;
    @Value("${maxActive}")
    private Integer maxActive;
    @Value("${maxWait}")
    private Integer maxWait;
    @Value("${minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${validationQuery}")
    private String validationQuery;
    @Value("${testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${testOnReturn}")
    private Boolean testOnReturn;
    @Value("${poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value("${maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${removeAbandoned}")
    private Boolean removeAbandoned;
    @Value("${removeAbandonedTimeout}")
    private Integer removeAbandonedTimeout;
    @Value("${logAbandoned}")
    private Boolean logAbandoned;
    @Value("${wall}")
    private Boolean wall;
    @Value("${stat}")
    private Boolean stat;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(passwrod);
        //配置初始化大小、最小、最大
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        //配置获取连接等待超时的时间
        dataSource.setMaxWait(maxWait);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
         // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);// value="true"/>
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);// value="20"/>
       // 打开removeAbandoned功能
        dataSource.setRemoveAbandoned(removeAbandoned);// value="true"/>
        //1800秒，也就是30分钟
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);// value="1800"/>
        //关闭abanded连接时输出错误日志
        dataSource.setLogAbandoned(logAbandoned);// value="true"/>
        // 插件
        List<Filter> filters = new ArrayList<>();
        if (wall) {
            WallConfig wallConfig = new WallConfig();
            wallConfig.setDir("orale");
            WallFilter wallFilter = new WallFilter();
            wallFilter.setDbType("oracle");
            wallFilter.setConfig(wallConfig);
            filters.add(wallFilter);
        }
        if (stat) {
            StatFilter statFilter = new StatFilter();
            statFilter.setSlowSqlMillis(10000);
            statFilter.setLogSlowSql(true);
            statFilter.setMergeSql(true);
            filters.add(statFilter);
        }
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
