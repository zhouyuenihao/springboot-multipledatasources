package com.mzd.multipledatasources.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyue01
 */
@Configuration
@MapperScan(basePackages = "com.mzd.multipledatasources.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
@EnableConfigurationProperties(Config.class)
public class DataSourceConfig {
    /*@Primary
    @Bean(name = "test1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource getDateSource2() {
        return DataSourceBuilder.create().build();
    }*/

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource(Config config) {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSource = new HashMap<>(10);
        config.getDataSources().forEach((k, v) -> {
            DataSource source = DataSourceBuilder.create()
                    .url(v.getUrl())
                    .username(v.getUsername())
                    .password(v.getPassword())
                    .driverClassName(v.getDriverClassName())
                    .build();
            targetDataSource.put(DataSourceType.DataBaseType.valueOf(k.toUpperCase()), source);
            if (v.isPrimary()) {
                dataSource.setDefaultTargetDataSource(source);
            }
        });
        dataSource.setTargetDataSources(targetDataSource);
        return dataSource;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
        return bean.getObject();
    }
}
