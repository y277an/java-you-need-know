package com.xiaohui;

import static org.junit.Assert.assertTrue;

import com.xiaohui.minimybatis.config.MapperRegistory;
import org.junit.Test;

import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void shouldAnswerWithTrue() throws ClassNotFoundException {
        String rootPath = this.getClass().getResource("/").getPath();


        MapperRegistory registory = new MapperRegistory(rootPath+ "com/xiaohui/minimybatis/mapper");
        Map<String, MapperRegistory.MapperData> map = registory.getMethodMaping();
        for (Map.Entry<String, MapperRegistory.MapperData> entry : map.entrySet()){
            System.out.println("methodName" + entry.getKey() + "|| sql:" + entry.getValue().getSql()+ "|| className:"
                    + entry.getValue().getType().getSimpleName());
        }
    }


}
