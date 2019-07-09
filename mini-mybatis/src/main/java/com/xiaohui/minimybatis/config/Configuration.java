package com.xiaohui.minimybatis.config;

import lombok.Data;

/**
 * @Author: xiaohui
 * @Description:
 */
@Data
public class Configuration {

    public static final String USER_NAME = "root";
    public static final String PASS_WORD = "mandy";
    public static final String DRIVER    = "com.mysql.cj.jdbc.Driver";
    public static final String URL       = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private final MapperRegistory mapperRegistory;

    private String scanPackageName;

    public Configuration(String scanPackageName) {
        this.scanPackageName = scanPackageName;
        mapperRegistory = new MapperRegistory(scanPackageName);
    }
}
