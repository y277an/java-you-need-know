package com.xiaohui.util;

/**
 * @Author: xiaohui
 * @Description: 使用雪花算法的id生成类
 * @Date: 2019/6/1 8:03
 */
public class IdUtil {

    private final static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    /**
     * 消息ID
     * @return
     */
    public static String getId(){
        return String.valueOf(idWorker.nextId());
    }
}
