package com.xiaohui.main.plugin;

import com.xiaohui.minimybatis.binding.MapperData;
import com.xiaohui.minimybatis.plugin.Interceptor;
import com.xiaohui.minimybatis.plugin.Intercepts;
import com.xiaohui.minimybatis.plugin.Invocation;
import com.xiaohui.minimybatis.plugin.Plugin;


import java.util.Properties;

/**
 * <p>执行日志输出插件</p>
 *
 * @author grand 2018/6/21
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人}
 * @modify by reason:{方法名}:{原因}
 */
@Intercepts({})
public class ExecutorLogPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MapperData mapperData = (MapperData)invocation.getArgs()[0];
        Object[] parameter = (Object[])invocation.getArgs()[1];
        System.out.println(("ExecutorLogPlugin is in processing...."));
        System.out.println("mapperData is :"+ mapperData);
        for (int i=0;i<parameter.length;i++) {
            System.out.println("parameter "+ i +" is　:" + parameter[i]);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object var1) {
        return Plugin.wrap(var1,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
