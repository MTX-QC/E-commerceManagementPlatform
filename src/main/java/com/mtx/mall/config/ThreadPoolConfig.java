package com.mtx.mall.config;

import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
    @Bean
    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(10, 20, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }
}
