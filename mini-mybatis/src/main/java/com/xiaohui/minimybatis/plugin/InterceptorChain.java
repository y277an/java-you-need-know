package com.xiaohui.minimybatis.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 拦截器链
 * @Author: xiaohui
 * @Description:
 */
public class InterceptorChain {

    //内部就是一个拦截器的List
    private final List<Interceptor> interceptors = new ArrayList<>();

    public Object pluginAll(Object target) {
        //循环调用每个Interceptor.plugin方法
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(interceptors);
    }

}
