package com.example.base.config;

import com.example.base.init.CraneContextDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncThreadPoolConfig {

    private static final int cpu = Runtime.getRuntime().availableProcessors();//获取当前机器CPU数量
    private static final int corePoolSize = cpu;       		// 核心线程数（默认线程数）
    private static final int maxPoolSize = cpu * 2;		    // 最大线程数
    private static final int keepAliveTime = 60;			// 允许线程空闲时间（单位：默认为秒）
    private static final int queueCapacity = 200;			// 缓冲队列数
    private static final String threadNamePrefix = "fileTaskExecutor-"; // 线程池名前缀

    @Bean("fileTaskExecutor") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolTaskExecutor fileTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new CraneContextDecorator());
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }
}