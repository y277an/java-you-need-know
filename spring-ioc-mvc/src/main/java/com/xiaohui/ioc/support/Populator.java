package com.xiaohui.ioc.support;

import com.xiaohui.ioc.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class Populator {

    public Populator() {
    }

    public void populate(Map<String, Object> instanceMapping) {

        if (instanceMapping.isEmpty()) return;

        // 循环遍历每一个容器中的对象
        for (Map.Entry<String, Object> entry : instanceMapping.entrySet()) {
            // 获取对象的字段
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) continue;
                Autowired autowired = field.getAnnotation(Autowired.class);
                // 字段要注入的id,value为空则按类名/接口名自动注入
                String id = autowired.value();
                if (StringUtils.isEmpty(id)) {
                    id = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    // 反射注入
                    field.set(entry.getValue(), instanceMapping.get(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}