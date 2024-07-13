package com.personal_projects.notifications_qpi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {


    @Bean("asyncExecutor")
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor taskExecutor= new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.setThreadNamePrefix("AysnExecutorNotifications-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
