package com.fpx.xinyou.conf;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.fpx.xinyou.oprtmapper.OprtDataMapper;
import com.github.pagehelper.PageHelper;


@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DataSourceConfig.class)
public class XMybatisConfig implements TransactionManagementConfigurer {

	@Autowired
	DataSource dataSource;
    
	
	@Resource(name = "oprtDs")
	DataSource oprtDs;
	
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.fpx.xinyou.model");
        
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Bean(name = "oprtSqlSessionFactory")
    public SqlSessionFactory oprtSqlSessionFactoryBean() {
    	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    	bean.setDataSource(oprtDs);
    	bean.setTypeAliasesPackage("com.fpx.xinyou.model");
    	
    	//分页插件
    	PageHelper pageHelper = new PageHelper();
    	Properties properties = new Properties();
    	properties.setProperty("reasonable", "true");
    	properties.setProperty("supportMethodsArguments", "true");
    	properties.setProperty("returnPageInfo", "check");
    	properties.setProperty("params", "count=countSql");
    	pageHelper.setProperties(properties);
    	
    	//添加插件
    	bean.setPlugins(new Interceptor[]{pageHelper});
    	
    	//添加XML目录
    	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    	try {
    		bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
    		return bean.getObject();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}
    }
    
    @Bean
    public OprtDataMapper getOprtDataMapper(){
    	SqlSessionFactory sf = oprtSqlSessionFactoryBean();
    	SqlSession session = sf.openSession();
    	OprtDataMapper mapper = session.getMapper(OprtDataMapper.class);
    	return mapper;
//    	sf.openSession(arg0);
//    	OprtDataMapper mapper = (OprtDataMapper)sf.openSession(OprtDataMapper.class);
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() {
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }
    
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
	 
}