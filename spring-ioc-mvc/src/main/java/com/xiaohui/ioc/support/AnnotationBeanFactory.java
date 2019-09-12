package com.xiaohui.ioc.support;

import com.xiaohui.ioc.beans.factory.BeanDefinitionRegistry;
import com.xiaohui.ioc.beans.factory.BeanFactory;
import com.xiaohui.ioc.beans.factory.BeanRegister;
import com.xiaohui.ioc.beans.factory.config.BeanDefinition;
import com.xiaohui.ioc.beans.factory.config.BeanDefinitionParser;
import com.xiaohui.ioc.beans.factory.config.BeanReference;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationBeanFactory implements BeanFactory, BeanDefinitionRegistry, BeanRegister {
    private Map<String, Object> singletonInstanceMapping = new ConcurrentHashMap<>();

    // 保存所有bean的信息,主要包含bean的类型  id等信息（初始化时，遍历该list，实例化所有bean）
    private Map<String, BeanDefinition> bdMap = new ConcurrentHashMap<>();

    // 配置文件我们使用properties文件，相比使用xml节省了很多解析xml的代码
    private Properties config = new Properties();

    //记录正在创建的bean
    private ThreadLocal<Set<String>> initialedBeans = new ThreadLocal<>();

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
        populator.populate(singletonInstanceMapping);
    }

    /**
     * 调用具体的创建对象创建bean
     */
    private void createBean() {
        bdMap.forEach((beanName,beanDefinition)-> doCreate(beanName));
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
        if (singletonInstanceMapping.containsKey(beanName)) {
            System.out.println("[" + beanName + "]已经存在");
            return;
        }
        if (beanDefinition != null && beanDefinition.isSingleton()) {
            singletonInstanceMapping.put(beanName, beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanName) {
        return singletonInstanceMapping.get(beanName);
    }

    public Properties getConfig() {
        return this.config;
    }

    @Override
    public Map<String, Object> getBeans() {
        return singletonInstanceMapping;
    }

    @Override
    public void registBeanDefinition(List<BeanDefinition> bds) {
        bds.forEach((bd)->{
            bdMap.put(bd.getBeanName(),bd);
        });
    }

    @Override
    public void registInstanceMapping(String id, Object instance) {
        singletonInstanceMapping.put(id, instance);
    }

    private Object doCreate(String beanName) {
        if (!singletonInstanceMapping.containsKey(beanName)) {
            System.out.println("[" + beanName + "]不存在");
        }

        // 记录正在创建的Bean
        Set<String> beans = this.initialedBeans.get();
        if (beans == null) {
            beans = new HashSet<>();
            this.initialedBeans.set(beans);
        }

        // 检测循环依赖
        if (beans.contains(beanName)) {
            System.out.println("检测到" + beanName + "存在循环依赖：" + beans);
        }

        // 记录正在创建的Bean
        beans.add(beanName);

        Object instance = singletonInstanceMapping.get(beanName);

        if (instance != null) {
            return instance;
        }

        //不存在则进行创建
        if (!this.bdMap.containsKey(beanName)) {
            System.out.println("不存在名为：[" + beanName + "]的bean定义,即将进行创建");
        }

        BeanDefinition bd = this.bdMap.get(beanName);

        Class<?> beanClass = bd.getBeanClass();

        if (beanClass != null) {
            instance = createBeanByConstruct(bd);
            if (instance == null) {
                instance = createBeanByStaticFactoryMethod(bd);
            }
        } else if (instance == null && StringUtils.isNotBlank(bd.getStaticCreateBeanMethod())) {
            instance = createBeanByFactoryMethod(bd);
        }

        this.doInit(bd, instance);

        //创建完成 移除该bean的记录
        beans.remove(beanName);

        if (instance != null && bd.isSingleton()) {
            registInstanceMapping(bd.getBeanName(), instance);
        }
        bdMap.put(beanName, bd);
        return instance;
    }

    private void doInit(BeanDefinition bd, Object instance) {
        Class<?> beanClass = instance.getClass();
        if (StringUtils.isNotBlank(bd.getBeanInitMethodName())) {
            try {
                Method method = beanClass.getMethod(bd.getBeanInitMethodName(), null);
                method.invoke(instance, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构造方法创建实例
     *
     * @param bd
     * @return
     */
    private Object createBeanByConstruct(BeanDefinition bd) {
        Object instance = null;
        try {
            //解析构造参数
            List<?> constructorArg = bd.getConstructorArg();
            Object[] objects = parseConstructorArgs(constructorArg);
            //匹配构造参数
            Constructor<?> constructor = matchConstructor(bd, objects);
            if (constructor != null) {
                instance = constructor.newInstance(objects);
            } else {
                instance = bd.getBeanClass().newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 普通工厂方法创建实例
     *
     * @param bd
     * @return
     */
    private Object createBeanByFactoryMethod(BeanDefinition bd) {
        Object instance = null;
        try {
            //获取工厂类
            Object factory = doCreate(bd.getBeanFactory());
            //获取创建实例的方法
            Method method = factory.getClass().getMethod(bd.getCreateBeanMethod());
            //执行方法
            instance = method.invoke(factory, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 静态方法创建实例
     *
     * @param bd
     * @return
     */
    private Object createBeanByStaticFactoryMethod(BeanDefinition bd) {
        Object instance = null;
        try {
            Class<?> beanClass = bd.getBeanClass();
            //获取创建实例的方法
            Method method = beanClass.getMethod(bd.getStaticCreateBeanMethod());
            instance = method.invoke(beanClass, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 解析传入的构造参数值
     *
     * @param constructorArgs
     * @return
     */
    private Object[] parseConstructorArgs(List constructorArgs) throws Exception {

        if (constructorArgs == null || constructorArgs.size() == 0) {
            return null;
        }

        Object[] args = new Object[constructorArgs.size()];
        for (int i = 0; i < constructorArgs.size(); i++) {
            Object arg = constructorArgs.get(i);
            Object value = null;
            if (arg instanceof BeanReference) {
                String beanName = ((BeanReference) arg).getBeanName();
                value = this.doCreate(beanName);
            } else if (arg instanceof List) {
                value = parseListArg((List) arg);
            } else if (arg instanceof Map) {
                //todo 处理map
            } else if (arg instanceof Properties) {
                //todo 处理属性文件
            } else {
                value = arg;
            }
            args[i] = value;
        }
        return args;
    }

    private List parseListArg(List arg) throws Exception {
        //遍历list
        List param = new LinkedList();
        for (Object value : arg) {
            Object res = new Object();
            if (arg instanceof BeanReference) {
                String beanName = ((BeanReference) value).getBeanName();
                res = this.doCreate(beanName);
            } else if (arg instanceof List) {
                //递归 因为list中可能还存有list
                res = parseListArg(arg);
            } else if (arg instanceof Map) {
                //todo 处理map
            } else if (arg instanceof Properties) {
                //todo 处理属性文件
            } else {
                res = arg;
            }
            param.add(res);
        }
        return param;
    }

    private Constructor<?> matchConstructor(BeanDefinition bd, Object[] args) throws Exception {
        //先进行精确匹配 如果能匹配到相应的构造方法 则后续不用进行
        if (args == null) {
            return bd.getBeanClass().getConstructor(null);
        }
        //如果已经缓存了 则直接返回
        if (bd.getConstructor() != null)
            return bd.getConstructor();

        int len = args.length;
        Class[] param = new Class[len];
        //构造参数列表
        for (int i = 0; i < len; i++) {
            param[i] = args[i].getClass();
        }
        //匹配
        Constructor constructor = null;
        try {
            constructor = bd.getBeanClass().getConstructor(param);
        } catch (Exception e) {
            //这里上面的代码如果没匹配到会抛出空指针异常
            //为了代码继续执行 这里我们来捕获 但是不需要做其他任何操作
        }
        if (constructor != null) {
            return constructor;
        }

        //未匹配到 继续匹配
        List<Constructor> firstFilterAfter = new LinkedList<>();
        Constructor[] constructors = bd.getBeanClass().getConstructors();
        //按参数个数匹配
        for (Constructor cons : constructors) {
            if (cons.getParameterCount() == len) {
                firstFilterAfter.add(cons);
            }
        }

        if (firstFilterAfter.size() == 1) {
            return firstFilterAfter.get(0);
        }
        if (firstFilterAfter.size() == 0) {
            throw new Exception("不存在对应的构造函数：" + args);
        }
        //按参数类型匹配
        //获取所有参数类型
        boolean isMatch = true;
        for (int i = 0; i < firstFilterAfter.size(); i++) {
            Class[] types = firstFilterAfter.get(i).getParameterTypes();
            for (int j = 0; j < types.length; j++) {
                if (types[j].isAssignableFrom(args[j].getClass())) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                //对于原型bean 缓存方法
                if (bd.isPrototype()) {
                    bd.setConstructor(firstFilterAfter.get(i));
                }
                return firstFilterAfter.get(i);
            }
        }
        //未能匹配到
        throw new Exception("不存在对应的构造函数：" + args);
    }
}


