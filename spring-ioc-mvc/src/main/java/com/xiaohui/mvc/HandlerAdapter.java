package com.xiaohui.mvc;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

public class HandlerAdapter {
    // 保存对应的RequestParam  value==>参数的位置
    // 或者HttpRequestServlet.getName()===>index
    private Map<String, Integer> paramType;

    public HandlerAdapter(Map<String, Integer> paramType) {
        this.paramType = paramType;
    }

    /**
     * 具体调用的方法
     *
     * @param req
     * @param resp
     * @param handler 和url匹配的handler
     * @throws Exception
     */
    public void handle(HttpServletRequest req, HttpServletResponse resp, Handler handler) throws Exception {
        // 获取要调用方法的全部参数类型
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        // 创建一个反射调用需要的参数值得数组,数组长度和参数长度一样
        Object[] paramValues = new Object[parameterTypes.length];
        /**
         * 如果参数类型-->index  map里面有HttpServletRequest
         * 就在这个index下的数组赋值req
         */
        if (paramType.containsKey(HttpServletRequest.class.getName())) {
            paramValues[paramType.get(HttpServletRequest.class.getName())] = req;
        }
        if (paramType.containsKey(HttpServletResponse.class.getName())) {
            paramValues[paramType.get(HttpServletResponse.class.getName())] = resp;
        }

        /**
         * 循环遍历RequestParam  value==>index
         * 如果拿到的value在请求参数里面有
         * 那么就从req中取出来赋值给数组
         *
         */
        for (Map.Entry<String, Integer> entry : paramType.entrySet()) {
            String paramName = entry.getKey();
            Integer index = entry.getValue();
            // 拿到请求name对应的value
            String[] values = req.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                String value = Arrays.toString(values).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                // 赋值给参数数组,并且把取出来的string类型转成我们参数的类型
                paramValues[index] = castValueType(value, parameterTypes[index]);
            }
        }
        // 最后反射调用Controller的method方法
        handler.getMethod().invoke(handler.getController(), paramValues);
    }

    private Object castValueType(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == int.class) {
            return Integer.valueOf(value).intValue();
        } else {
            return null;
        }
    }

    public Map<String, Integer> getParamType() {
        return paramType;
    }

    public void setParamType(Map<String, Integer> paramType) {
        this.paramType = paramType;
    }
}