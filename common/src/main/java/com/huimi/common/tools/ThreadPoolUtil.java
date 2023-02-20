/*
 * Copyright (c) 2015-2017, HuiMi Tec co.,LTD. 枫亭子 (646496765@qq.com).
 */
package com.huimi.common.tools;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author 枫亭
 * @description 线程池创建工具类
 * @date 2018/2/6 10:38.
 */
public class ThreadPoolUtil {

    /**
     * 获取线程池
     *
     * @return pool
     */
    public static ExecutorService getPool(String threadName) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadName + "-pool-%d").build();

        return new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
}
