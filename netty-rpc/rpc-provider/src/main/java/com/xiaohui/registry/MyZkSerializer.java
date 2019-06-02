package com.xiaohui.registry;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.nio.charset.Charset;

/**
 * @Author: xiaohui
 * @Description: 给zookeeper序列化设定编码
 * @Date: 2019/6/1 16:53
 */
public class MyZkSerializer implements ZkSerializer
{
    public Object deserialize(byte[] bytes) throws ZkMarshallingError
    {
        return new String(bytes, Charset.forName("UTF-8"));
    }

    public byte[] serialize(Object obj) throws ZkMarshallingError
    {
        return String.valueOf(obj).getBytes(Charset.forName("UTF-8"));
    }
}

