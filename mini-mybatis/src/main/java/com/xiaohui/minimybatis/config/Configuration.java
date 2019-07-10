package com.xiaohui.minimybatis.config;

import lombok.Data;
import com.xiaohui.minimybatis.binding.MapperRegistry;

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

    private MapperRegistry mapperRegistry;

    private String scanPackageName;

    /**
     * 扫描指定包路径
     *
     * @param scanPackageName
     */
    public Configuration(String scanPackageName) {
        this.scanPackageName = scanPackageName;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 记载配置文件，properties或者xml解析
        loadConfigProperties();
        // 初始化数据源信息
        initDataSource();
        // 解析并加载mapper文件
        loadMapperRegistry();
        // 解析加载plugin
        initPluginChain();
        // 解析加载typeHandler
        initTypeHandler();
    }

    /**
     * 记载配置文件，properties或者xml解析
     */
    private void loadConfigProperties() {
    }

    /**
     * 初始化数据源信息
     */
    private void initDataSource() {
    }

    /**
     * 解析并加载mapper文件
     */
    private void loadMapperRegistry() {
        mapperRegistry = new MapperRegistry(scanPackageName);
    }

    /**
     * 解析加载plugin
     */
    private void initPluginChain() {
    }

    /**
     * 解析加载typeHandler
     */
    private void initTypeHandler() {
    }
}
