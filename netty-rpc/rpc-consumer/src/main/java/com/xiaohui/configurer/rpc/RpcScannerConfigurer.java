package com.xiaohui.configurer.rpc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @Author: xiaohui
 * @Description: 扫描RPC接口，并由Spring生命周期托管
 * @Date: 2019/6/1 17:45
 */
@Component
public class RpcScannerConfigurer implements BeanDefinitionRegistryPostProcessor {

    String                      basePackage = "com.xiaohui.service";
    // 需要定制化扫描的注解
    Class<? extends Annotation> annotation  = null;

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathRpcScanner scanner = new ClassPathRpcScanner(beanDefinitionRegistry);
        scanner.setAnnotationClass(annotation);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
