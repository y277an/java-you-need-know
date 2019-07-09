package com.xiaohui.minimybatis.config;

import com.xiaohui.minimybatis.Bootstrap;
import com.xiaohui.minimybatis.annotation.AnnotationUtil;
import com.xiaohui.minimybatis.annotation.Table;
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
public class MapperRegistory {

    /**
     * 用于保存 方法名与sql映射关系
     */
    private final Map<String, MapperData> methodMaping = new HashMap<>();

    private String packageName;

    private String realPackagePath;

    public MapperRegistory(String packageName) {
        this.packageName = packageName;
        String bootstrapPath = Bootstrap.class.getResource("/").getPath();

        // 获取
        realPackagePath = bootstrapPath + StringUtils.replaceAllChar(packageName, '.', '/');

        init();

    }

    /**
     * 初始化
     */
    private void init(){
        File file = new File(realPackagePath);

        if (file.isDirectory()){
            try {
                for (File file1 : file.listFiles()){
                    Class clazz = fileToClass(file1);
                    String tableName = AnnotationUtil.getAnnotationValue(clazz.getAnnotation(Table.class));

                    String methodName = "";
                    for (Method method : clazz.getMethods()){
                        Annotation[] methodAnnotations = method.getAnnotations();
                        if (null != methodAnnotations){
                            methodName = clazz.getName() + "." + method.getName();

                            Annotation methodAnnotation = methodAnnotations[0];
                            String sql = AnnotationUtil.getAnnotationValue(methodAnnotation);

                            methodMaping.put(methodName, new MapperData(sql, method.getReturnType(), tableName));
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("没有该目录！");
        }

    }



    /**
     * 将文件转换为class对象
     * @param file
     * @return
     * @throws ClassNotFoundException
     */
    private Class fileToClass(File file) throws ClassNotFoundException {
        String className = this.packageName + "." + file.getName().replace(".class", "");
        return Class.forName(className);
    }


    /**
     *  用于存储注解内容
     */
    public Map<String, MapperData> getMethodMaping() {
        return methodMaping;
    }

    public class MapperData{

        private String sql;

        private Class type;

        private String tableName;

        public MapperData(String sql, Class type, String tableName) {
            this.sql = sql;
            this.type = type;
            this.tableName = tableName;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }
}
