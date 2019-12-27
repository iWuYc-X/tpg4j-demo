package com.iwuyc.tools.demo.tpg4j;


import com.google.common.util.concurrent.SettableFuture;
import com.iwuyc.tools.tpg4j.thread.ThreadPoolService;
import com.iwuyc.tools.tpg4j.thread.ThreadPoolServiceHolder;
import com.iwuyc.tools.tpg4j.thread.conf.Config;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class Bootstrap {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 配置文件。可以为空，如果为空，则使用默认的配置。
        String thframeYaml = null;
        ThreadPoolService service = Config.config(thframeYaml);

        // 方式1：由config返回的ThreadPoolsService实例获取线程池
        final SettableFuture<Void> end = SettableFuture.create();
        ExecutorService pools1 = service.getExecutorService(Bootstrap.class);
        pools1.submit(() -> {
            System.out.println("hello world!!");
            end.set(null);
        });
        System.out.println(pools1);

        // 方式2：由ThreadPoolsServiceHolder获取线程池
        ExecutorService pools2 = ThreadPoolServiceHolder.get(Bootstrap.class);
        // print: true
        assert  pools1 == pools2;
        end.get();
    }
}
