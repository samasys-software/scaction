package com.samayu.sca;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
public class Application extends SpringBootServletInitializer {

    public final static String CASTING_CALL_MESSAGE_QUEUE = "sactioncastingcall";


    @Autowired
    private ActiveMQConnectionFactory connectionFactory;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        }; 
    }

    String BROKER_URL = "tcp://localhost:61616";
    String BROKER_USERNAME = "admin";
    String BROKER_PASSWORD = "admin";

    @Bean(name="topicJmsTemplate")
    public JmsTemplate createJmsTemplateForTopic(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory( connectionFactory );
        template.setPubSubDomain( true );

        return template;
    }

    @Bean(name="queueJmsTemplate")
    public JmsTemplate createJmsTemplateForQueue(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory( connectionFactory );
        return template;
    }

    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setPassword(BROKER_USERNAME);
        connectionFactory.setUserName(BROKER_PASSWORD);

        return connectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory,
                                                             DefaultJmsListenerContainerFactoryConfigurer configurer){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}