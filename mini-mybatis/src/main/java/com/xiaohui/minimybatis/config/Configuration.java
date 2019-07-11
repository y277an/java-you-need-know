package com.xiaohui.minimybatis.config;

import com.xiaohui.minimybatis.executor.Executor;
import com.xiaohui.minimybatis.executor.SimpleExecutor;
import com.xiaohui.minimybatis.plugin.Interceptor;
import com.xiaohui.minimybatis.plugin.InterceptorChain;
import lombok.Data;
import com.xiaohui.minimybatis.binding.MapperRegistry;


/**
 * @Author: xiaohui
 * @Description:配置，源码中有更多的配置
 */
@Data
public class Configuration {

    public static final String USER_NAME = "root";
    public static final String PASS_WORD = "mandy";
    public static final String DRIVER    = "com.mysql.cj.jdbc.Driver";
    public static final String URL       = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private MapperRegistry mapperRegistry;
    private InterceptorChain interceptorChain = new InterceptorChain();
    private String scanPackageName;
    private String pluginName;

    /**
     * 扫描指定包路径
     *
     * @param scanPackageName
     */
    public Configuration(String scanPackageName,String pluginName) {
        this.scanPackageName = scanPackageName;
        this.pluginName = pluginName;
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
        String pluginStr = pluginName;
        String[] pluginArray = pluginStr.split(",");
        for (String plugin:pluginArray){
            Class clazz = null;
            try {
                clazz = this.getClass().getClassLoader().loadClass(plugin);
                if(clazz!=null){
                    Object o = clazz.newInstance();
                    this.interceptorChain.addInterceptor((Interceptor)clazz.newInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析加载typeHandler
     */
    private void initTypeHandler() {
    }

    public Executor newExecutor(){
        Executor executor = new SimpleExecutor(this);
        return (Executor)this.interceptorChain.pluginAll(executor);
    }
}
