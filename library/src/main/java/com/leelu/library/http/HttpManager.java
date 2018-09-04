package com.leelu.library.http;

import android.app.Application;

/**
 * @author : leelu
 * CreateTime : 2018/8/3 13:42
 * Description : 网络请求入口
 */
public class HttpManager {
    private volatile static HttpManager singleton;
    private static Application sContext;

    public static HttpManager getInstance() {
        testInitialize();
        if (singleton == null) {
            synchronized (HttpManager.class) {
                if (singleton == null) {
                    singleton = new HttpManager();
                }
            }
        }
        return singleton;
    }

    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    public static void init(Application app) {
        sContext = app;
    }

    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 HttpManager.init() 初始化！");
        }
    }
}
