package com.xiaohui.ioc.beans.factory.config;

import com.xiaohui.ioc.beans.factory.annotation.Component;
import com.xiaohui.ioc.beans.factory.annotation.Controller;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinitionGenerator {

    /**
     * 生成Bean元数据数组，为接下来实例化做准备
     *
     * @param className
     * @return
     */
    public static List<BeanDefinition> generate(String className) {
        try {
            Class clazz = Class.forName(className);
            String[] ids = generateIds(clazz);
            if (ids == null) return null;
            List<BeanDefinition> list = new ArrayList<>();
            for (String id : ids) {
                list.add(new BeanDefinition(id, clazz));
            }
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成存储类名的数组
     * 1.带有@Controller注解,用类的全名作为id返回ids长度为1
     * 2.@Component没有value时获取所有的实现的接口,接口名为id,返回ids数组
     * 长度是实现的接口个数
     * 3.@Component 有value 返回id=value
     * 4.不带容器要实例化的注解  null
     */
    private static String[] generateIds(Class clazz) {
        String[] ids = null;
        if (clazz.isAnnotationPresent(Controller.class)) {
            ids = new String[]{clazz.getName()};
        } else if (clazz.isAnnotationPresent(Component.class)) {
            Component component = (Component) clazz.getAnnotation(Component.class);
            String value = component.value();
            if (!StringUtils.isEmpty(value)) {
                ids = new String[]{value};
            } else {
                Class<?>[] interfaces = clazz.getInterfaces();
                ids = new String[interfaces.length];
                // 如果这个类实现了接口，就用接口的类型作为id
                for (int i = 0; i < interfaces.length; i++) {
                    ids[i] = interfaces[i].getName();
                }
                return ids;
            }
        }
        return ids;
    }

}