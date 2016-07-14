package com.fpx.xinyou.conf;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
//@EnableConfigurationProperties({DataSourceBaseSetting.class,DataSourceOprtSetting.class})
public class DataSourceConfig  {

  
    
    @Bean(name="baseDs")
    @Primary
    @ConfigurationProperties(prefix="base.datasource")
    public DataSource baseDataSource(){
    	 return DataSourceBuilder.create().build();
    }

    
    @Bean(name="oprtDs")
    @ConfigurationProperties(prefix="oprt.datasource")
    public DataSource oprtDataSource(){
//    	System.out.println("spring.datasource.url === "+env.getProperty("spring.datasource.url"));
//    	DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//		dataSource.setUrl("jdbc:oracle:thin:@172.16.30.26:1521:fz");
//		dataSource.setUsername("oprt");
//		dataSource.setPassword("oprt");
//		dataSource.setMaxActive(20);
//		try {
//			dataSource.setFilters("stat");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		dataSource.setInitialSize(1);
//		dataSource.setMaxWait(60000);
//		dataSource.setMinIdle(1);
//		dataSource.setTimeBetweenEvictionRunsMillis(60000);
//		dataSource.setMinEvictableIdleTimeMillis(300000);
//		dataSource.setValidationQuery("select 'x' FROM DUAL");
//		dataSource.setTestWhileIdle(true);
//		dataSource.setTestOnBorrow(false);
//		dataSource.setTestOnReturn(false);
//		dataSource.setPoolPreparedStatements(true);
//		dataSource.setMaxOpenPreparedStatements(20);
//		
//		return dataSource;
    	return DataSourceBuilder.create().build();
    }
    
    @Bean(name="ctrDs")
    @ConfigurationProperties(prefix="ctr.datasource")
    public DataSource ctrDataSource(){
 
    	return DataSourceBuilder.create().build();
    }
    
 
}