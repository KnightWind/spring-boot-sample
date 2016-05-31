package com.fpx.xinyou.conf;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fpx.xinyou.components.MessageAdapterHandler;
import com.fpx.xinyou.components.ScansDataMQProcess;
import com.fpx.xinyou.service.ScansDataService;
import com.fpx.xinyou.util.CodecFactory;
import com.fpx.xinyou.util.EventProcesser;
import com.fpx.xinyou.util.HessionCodecFactory;
import com.rabbitmq.client.Channel;


@Configuration
public class AmqpConfig {
	
	public static final String EXCHANGE   = "xy-data-exchange";
	public static final String QUEUE   = "xy-data-queue";
    public static final String ROUTINGKEY = "xy-data-routingkey";
    
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("172.16.30.50:5672");
        connectionFactory.setUsername("lz");
        connectionFactory.setPassword("0");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }
    
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }
    
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE);
    }
    
    
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true); //队列持久

    }
    
    
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(AmqpConfig.ROUTINGKEY);
    }
    
    @Bean
    public CodecFactory codec(){
    	return new HessionCodecFactory();
    }
    
    @Bean
    public EventProcesser scdProcess(){
    	return new ScansDataMQProcess(codec());
    }
    
    @Bean 
    public MessageAdapterHandler scdMsgHandler(){
    	return new MessageAdapterHandler(scdProcess());
    }
    
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
//    	MessageConverter serializerMessageConverter = new SerializerMessageConverter();
//    	MessageListener listener = new MessageListenerAdapter(scdMsgHandler(),serializerMessageConverter);
//      container.setMessageListener(listener);
    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
	            	byte[] body = message.getBody();
	            	String msg = new String(body);
	            	//System.out.println("消费者收到消息！");
	                //System.out.println("receive msg : " + msg);
	                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
	                ScansDataService.getInstance().process(msg);
            }
        });
        return container;
    }
}
