package com.fpx.xinyou.conf;

import java.util.Properties;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@AutoConfigureAfter(XMybatisConfig.class)
public class XMyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.fpx.xinyou.mapper");
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.fpx.xinyou.util.MyMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("order", "before");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

}
