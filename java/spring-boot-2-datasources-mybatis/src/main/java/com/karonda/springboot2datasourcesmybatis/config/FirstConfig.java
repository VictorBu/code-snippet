package com.karonda.springboot2datasourcesmybatis.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages  = {"com.karonda.springboot2datasourcesmybatis.dao.first"},// dao 层所在的包
        sqlSessionTemplateRef = "firstSqlSessionTemplate")
public class FirstConfig {

    @Bean
    @Primary
    public SqlSessionTemplate firstSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(firstSqlSessionFactory());
    }

    @Bean
    @Primary
    public DataSourceTransactionManager firstTransactionManager(){
        return new DataSourceTransactionManager(firstDataSource());
    }

    @Bean
    @Primary
    public SqlSessionFactory firstSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(firstDataSource());
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/first/*.xml")); // xml 所在路径
        return factoryBean.getObject();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.first")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.first.configuration")
    public DataSource firstDataSource() {
        return firstDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class) // 可以显示指定连接池，也可以不显示指定；即此行代码可以注释掉
                .build();
    }

}
