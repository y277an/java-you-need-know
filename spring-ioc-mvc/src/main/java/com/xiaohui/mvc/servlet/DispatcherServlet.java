package com.xiaohui.mvc.servlet;


import com.xiaohui.ioc.annotation.Controller;
import com.xiaohui.ioc.annotation.RequestMapping;
import com.xiaohui.ioc.annotation.RequestParam;
import com.xiaohui.ioc.support.AnnotationBeanFactory;
import com.xiaohui.mvc.Handler;
import com.xiaohui.mvc.HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {

    public static final String                       CONTEXT_CONFIG_LOCATION = "globalConfig";
    private             List<Handler>                handlerMapping          = new ArrayList<>();
    private             Map<Handler, HandlerAdapter> adapterMapping          = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        // 取出来web.xml中配置的param参数
        String location = getInitParameter(CONTEXT_CONFIG_LOCATION);
        // 创建ApplicationContext上下文,启动bean的解析  创建  注入等过程
        AnnotationBeanFactory context = new AnnotationBeanFactory(location);

        // 请求解析
        initMultipartResolver(context);
        // 多语言、国际化
        initLocaleResolver(context);
        // 主题View层的
        initThemeResolver(context);

        // 解析url和Method的关联关系
        initHandlerMappings(context);
        // 适配器（匹配的过程）
        initHandlerAdapters(context);

        // 异常解析
        initHandlerExceptionResolvers(context);
        // 视图转发（根据视图名字匹配到一个具体模板）
        initRequestToViewNameTranslator(context);

        // 解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
        initViewResolvers(context);

        initFlashMapManager(context);

        System.out.println("GPSpring MVC is init.");
    }

    private void initFlashMapManager(AnnotationBeanFactory context) {
    }

    private void initViewResolvers(AnnotationBeanFactory context) {
    }

    private void initRequestToViewNameTranslator(AnnotationBeanFactory context) {
    }

    private void initHandlerExceptionResolvers(AnnotationBeanFactory context) {
    }

    private void initHandlerAdapters(AnnotationBeanFactory context) {
        if (handlerMapping.isEmpty()) return;
        // 遍历所有的handlerMapping
        for (Handler handler : handlerMapping) {
            Method method = handler.getMethod();
            // 创建一个保存RequestParam 注解的value(即参数名)==>index(参数位置索引)
            Map<String, Integer> paramType = new HashMap<String, Integer>();
            // 获取所有的参数类型数组
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                // 如果有HttpServletRequest类型就往map中保存 类型名==>index
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramType.put(type.getName(), i);
                }
            }

            // 获取所有的参数注解,之所以返回二维数组,是因为每个参数可能有
            // 多个注解修饰
            Annotation[][] pas = method.getParameterAnnotations();
            for (int i = 0; i < pas.length; i++) {
                // 获取第i个参数的修饰注解数组
                Annotation[] pa = pas[i];
                // 遍历每个参数的修饰注解
                for (Annotation a : pa) {
                    if (a instanceof RequestParam) {
                        String paramName = ((RequestParam) a).value();
                        if (!"".equals(paramName)) {
                            // 如果注解属于@RequestParam
                            // 把注解参数 name==>index保存map
                            paramType.put(paramName, i);
                        }
                    }
                }
            }
            adapterMapping.put(handler, new HandlerAdapter(paramType));
        }

    }

    /**
     * 初始化handlerMappings
     *
     * @param context
     */
    private void initHandlerMappings(AnnotationBeanFactory context) {
        // 获取context中所有的bean
        Map<String, Object> beans = context.getBeans();
        if (beans.isEmpty()) return;
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            // 只对controller修饰的类做解析
            if (!entry.getValue().getClass().isAnnotationPresent(Controller.class)) continue;
            String url = "";
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                // 先取类上面的url
                url = clazz.getAnnotation(RequestMapping.class).value();
            }

            Method[] methods = clazz.getMethods();
            // 再取对应方法上的url
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) continue;
                String subUrl = method.getAnnotation(RequestMapping.class).value();
                String regex = (url + subUrl).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                // 添加到handlerMapping中去
                handlerMapping.add(new Handler(entry.getValue(), method, pattern));
            }

        }
    }

    private void initThemeResolver(AnnotationBeanFactory context) {
    }

    private void initLocaleResolver(AnnotationBeanFactory context) {
    }

    private void initMultipartResolver(AnnotationBeanFactory context) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        // 取出匹配的handler，如果没有则跳过
        Optional.ofNullable(getHandler(req)).ifPresent(handler -> {
            // 根据handler取出HandlerAdapter
            HandlerAdapter ha = getHandlerAdapter(handler);

            // 调用handle方法处理请求,暂时未做ModalAndView处理
            try {
                ha.handle(req, resp, handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private HandlerAdapter getHandlerAdapter(Handler handler) {
        if (adapterMapping.isEmpty()) return null;
        return adapterMapping.get(handler);
    }

    private Handler getHandler(HttpServletRequest req) {
        if (handlerMapping.isEmpty()) return null;
        String contextPath = req.getContextPath();
        String url = req.getRequestURI();
        // 获取请求的url  除去contextPath剩余的
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (Handler handler : handlerMapping) {
            if (handler.getPattern().matcher(url).matches()) {
                // 匹配到就把handler返回
                return handler;
            }
        }
        return null;
    }
}
