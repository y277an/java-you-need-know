package com.xiaohui.ioc.support;

import com.xiaohui.ioc.beans.factory.BeanDefinitionRegistry;
import com.xiaohui.ioc.beans.factory.BeanFactory;
import com.xiaohui.ioc.beans.factory.BeanRegister;
import com.xiaohui.ioc.beans.factory.config.BeanDefinition;
import com.xiaohui.ioc.beans.factory.config.BeanDefinitionParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationBeanFactory implements BeanFactory, BeanDefinitionRegistry, BeanRegister {
    private Map<String, Object> instanceMapping = new ConcurrentHashMap<>();

    // 保存所有bean的信息,主要包含bean的类型  id等信息（初始化时，遍历该list，实例化所有bean）
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    // 配置文件我们使用properties文件，相比使用xml节省了很多解析xml的代码
    private Properties config = new Properties();

    public AnnotationBeanFactory(String location) {
        InputStream is = null;
        try {

            // 1、查找配置文件
            is = this.getClass().getClassLoader().getResourceAsStream(location);

            // 2、载入配置文件
            config.load(is);

            // 3、扫描基础包
            register();

            // 4、实例化所有Bean
            createBean();

            // 5、依赖注入
            populate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用具体委派的注入类进行注入
     */
    private void populate() {
        Populator populator = new Populator();
        populator.populate(instanceMapping);
    }

    /**
     * 调用具体的创建对象创建bean
     */
    private void createBean() {
        BeanCreater creater = new BeanCreater(this);
        creater.create(beanDefinitions);
    }

    /**
     * 调用具体的注册对象注册bean信息
     */
    private void register() {
        BeanDefinitionParser parser = new BeanDefinitionParser(this);
        parser.parse(config);
    }

    /**
     * 注册单个bean信息
     */
    @Override
    public void register(String beanName, BeanDefinition beanDefinition) {
        if (instanceMapping.containsKey(beanName)) {
            System.out.println("[" + beanName + "]已经存在");
            return;
        }
        instanceMapping.put(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName) {
        return instanceMapping.get(beanName);
    }

    public Properties getConfig() {
        return this.config;
    }

    @Override
    public Map<String, Object> getBeans() {
        return instanceMapping;
    }

    @Override
    public void registBeanDefinition(List<BeanDefinition> bds) {
        beanDefinitions.addAll(bds);
    }

    @Override
    public void registInstanceMapping(String id, Object instance) {
        instanceMapping.put(id, instance);
    }


}
