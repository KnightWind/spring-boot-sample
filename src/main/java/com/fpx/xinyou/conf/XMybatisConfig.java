package com.fpx.xinyou.conf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.fpx.xinyou.mapper.DataDictionaryMapper;
import com.fpx.xinyou.model.XtdTrackMappingItem;
import com.fpx.xinyou.oprtmapper.OprtDataMapper;
import com.fpx.xinyou.service.StSnMappingDictionary;
import com.fpx.xinyou.service.TrackMappingDictionary;
import com.github.pagehelper.PageHelper;


@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DataSourceConfig.class)
public class XMybatisConfig implements TransactionManagementConfigurer {

	@Autowired
	DataSource dataSource;
    
	
	@Resource(name = "oprtDs")
	DataSource oprtDs;
	
	
	@Resource(name = "ctrDs")
	DataSource ctrDs;
	
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
    
    
    public SqlSessionFactory ctrSqlSessionFactoryBean() {
    	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    	bean.setDataSource(ctrDs);
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
    }
    
    /**
     * 初始化数据字典的mapper
     * @return
     */
    public DataDictionaryMapper getTsvCodeMapper(){
    	SqlSessionFactory sf = ctrSqlSessionFactoryBean();
    	SqlSession session = sf.openSession();
    	DataDictionaryMapper mapper = session.getMapper(DataDictionaryMapper.class);
    	return mapper;
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
    
    
    
    @Bean(name="stSnMapping")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public StSnMappingDictionary getStSnMappingDictionary() {
        
    	StSnMappingDictionary  ssd =  new StSnMappingDictionary();
    	DataDictionaryMapper tcmapper = getTsvCodeMapper();
    	List<Map<String,Object>> datas = tcmapper.getAllTsvMapping();
    	
    	Map<String,String> mapping = new HashMap<String,String>();
    	//System.out.println("datas size: "+datas.size());
    	if(datas != null && !datas.isEmpty()) {
    		for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				//System.out.println(map);
				String sn_Code = String.valueOf(map.get("SN_CODE"));
				String sn_Value = String.valueOf(map.get("SN_VALUE"));
				mapping.put(sn_Value, sn_Code);
			}
    	}
    	//System.out.println("the mapping data is :"+ mapping);
    	ssd.setMappingDatas(mapping);
    	return ssd;
    }
    
    @Bean(name="xtdTrackMapping")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TrackMappingDictionary getXtdTrackMappingDictionary() {
    	TrackMappingDictionary  tmd =  new TrackMappingDictionary();
    	DataDictionaryMapper tcmapper = getTsvCodeMapper();
    	
    	List<XtdTrackMappingItem> datas = tcmapper.getXtdTrackMappings();
    	tmd.setDatas(datas);
    	
    	System.out.println("datas size is : "+datas.size());
    	return tmd;
    }
    
	 
}