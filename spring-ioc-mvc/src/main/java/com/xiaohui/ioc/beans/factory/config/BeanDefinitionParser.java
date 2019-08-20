package com.xiaohui.ioc.beans.factory.config;


import com.xiaohui.ioc.beans.factory.BeanRegister;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BeanDefinitionParser {
    // 配置的扫描包的key
    public static final String SCAN_PACKAGE = "scanPackage";
    // 容器注册对象
    private BeanRegister register;

    public BeanDefinitionParser(BeanRegister register) {
        this.register = register;
    }

    public void parse(Properties properties) {
        // 获取要扫描的包
        String packageName = properties.getProperty(SCAN_PACKAGE);
        // 执行注册
        doRegister(packageName);
    }


    private void doRegister(String packageName) {
        // 获取此包名下的绝对路径
        URL url = getClass().getClassLoader().getResource("./" + packageName.replaceAll("\\.", "/"));
        Optional.ofNullable(url.getFile()).ifPresent( path ->{
            File dir = new File(path);
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    // 文件夹-->递归继续执行
                    doRegister(packageName + "." + file.getName());
                } else {
                    // 处理文件名来获取类名  运行时获取到的是class文件
                    String className = packageName + "." + file.getName().replaceAll(".class", "").trim();
                    // 调用BeanDefinitionGenerator.generate(className)方法,来处理
                    // 1.类带有容器要处理的注解,则解析id生成BeanDefinition集合返回
                    // 2.不带有需要处理的注解   直接返回null
                    List<BeanDefinition> definitions = BeanDefinitionGenerator.generate(className);
                    if (definitions == null) continue;
                    // 调用容器的注册方法来完成bean信息的注册
                    this.register.registBeanDefinition(definitions);
                }
            }
        });
    }
}
