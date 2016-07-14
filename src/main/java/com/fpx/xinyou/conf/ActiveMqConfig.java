package com.fpx.xinyou.conf;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import com.fpx.xinyou.util.BaseConfig;

@Configuration
public class ActiveMqConfig{
    public static final String CN_QUEUE = BaseConfig.getInstance().getString("cainiao.mq.queue");
    
    private static final Logger logger = Logger.getLogger(ActiveMqConfig.class);
    
    @Bean(name="caiNiaoTrackQueue")
    public Queue caiNiaoJMSQueue() {
        return new ActiveMQQueue(CN_QUEUE);
    }
    
    
    //@Bean(name="targetConnectionFactory")
    public ConnectionFactory getTargetConnectionFactory(){
    	String url = BaseConfig.getInstance().getString("cainiao.mq.url");
    	logger.info("will connecting active mq:"+url);
    	return new ActiveMQConnectionFactory("tcp://172.16.30.23:61616");
    }
    
    
    //@Bean(name="pooledConnectionFactory")
    public PooledConnectionFactory getPooledConnectionFactory(){
    	PooledConnectionFactory pooledFactory =  new PooledConnectionFactory();
    	pooledFactory.setConnectionFactory(getTargetConnectionFactory());
    	pooledFactory.setMaxConnections(10);
    	return pooledFactory;
    }
    
    
    public ConnectionFactory getSingleConnectionFactory(){
    	SingleConnectionFactory scf = new SingleConnectionFactory();
    	scf.setTargetConnectionFactory(getPooledConnectionFactory());
    	return scf;
    }
    
    @Bean
    public JmsTemplate activeMqTemplate() {
    	
    	JmsTemplate jt = new JmsTemplate();
    	jt.setSessionAcknowledgeMode(1);
    	jt.setConnectionFactory(getSingleConnectionFactory());
    	return jt; 
    }
}
