package com.foundation.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class CustomAsyncConfig implements AsyncConfigurer {

   @Override
    public Executor getAsyncExecutor() {
       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
       executor.setCorePoolSize(100);
       executor.setMaxPoolSize(Integer.MAX_VALUE);
       executor.setQueueCapacity(Integer.MAX_VALUE);
       executor.setThreadNamePrefix("CustomAsyncExecutor-");
       executor.initialize();
       return executor;
   }
}
