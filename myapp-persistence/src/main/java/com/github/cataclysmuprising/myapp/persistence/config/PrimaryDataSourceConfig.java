/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-persistence - PrimaryDataSourceConfig.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprising.myapp.persistence.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = { "com.github.cataclysmuprising.myapp.common.mybatis.mapper", "com.github.cataclysmuprising.myapp.persistence.mapper" }, sqlSessionFactoryRef = PrimaryDataSourceConfig.SQL_SESSION_FACTORY_NAME)
public class PrimaryDataSourceConfig {

    public static final String SQL_SESSION_FACTORY_NAME = "primarySqlSessionFactory";
    public static final String TX_MANAGER = "primaryTransactionManager";

    @Bean(name = "primaryDSConfig")
    @ConfigurationProperties(prefix = "datasource.primary-ds")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource(@Qualifier("primaryDSConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean(name = PrimaryDataSourceConfig.SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));
        bean.setTypeAliasesPackage("com.github.cataclysmuprising.myapp.domain.bean");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = PrimaryDataSourceConfig.TX_MANAGER)
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
