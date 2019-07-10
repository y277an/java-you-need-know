package com.xiaohui.minimybatis.binding;

import com.xiaohui.minimybatis.Bootstrap;
import com.xiaohui.minimybatis.annotations.AnnotationUtil;
import com.xiaohui.minimybatis.annotations.Table;
import com.xiaohui.minimybatis.utils.StringUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaohui
 * @Description:
 */
public class MapperRegistry {

    /**
     * 用于保存方法名与SQL等信息的映射关系
     */
    private final Map<String, MapperData> methodMaping = new HashMap<>();

    private String packageName;

    private String filePath;

    /**
     * 扫描指定路径，给methodMaping赋值
     *
     * @param packageName
     */
    public MapperRegistry(String packageName) {
        this.packageName = packageName;
        String bootstrapPath = Bootstrap.class.getResource("/").getPath();

        // 获取
        filePath = bootstrapPath + StringUtils.replaceAllChar(packageName, '.', '/');

        init();

    }

    /**
     * 扫描带@Table注解的
     */
    private void init() {
        File file = new File(filePath);

        if (file.isDirectory()) {
            try {
                for (File file1 : file.listFiles()) {
                    Class clazz = fileToClass(file1);
                    String tableName = AnnotationUtil.getAnnotationValue(clazz.getAnnotation(Table.class));

                    String methodName;
                    for (Method method : clazz.getMethods()) {
                        Annotation[] methodAnnotations = method.getAnnotations();
                        if (methodAnnotations != null && methodAnnotations.length > 0) {
                            methodName = clazz.getName() + "." + method.getName();

                            // 获取mapper方法上的select、insert等注解
                            Annotation methodAnnotation = methodAnnotations[0];
                            String sql = AnnotationUtil.getAnnotationValue(methodAnnotation);

                            methodMaping.put(methodName, new MapperData(sql, method.getReturnType(), tableName));
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("没有该目录！");
        }

    }


    /**
     * 将文件转换为class对象
     *
     * @param file
     */
    private Class fileToClass(File file) throws ClassNotFoundException {
        String className = this.packageName + "." + file.getName().replace(".class", "");
        return Class.forName(className);
    }


    /**
     * 用于存储注解内容
     */
    public Map<String, MapperData> getMethodMaping() {
        return methodMaping;
    }


}
