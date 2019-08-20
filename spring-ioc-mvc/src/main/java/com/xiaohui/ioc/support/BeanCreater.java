package com.xiaohui.ioc.support;

import com.xiaohui.ioc.beans.factory.BeanRegister;
import com.xiaohui.ioc.beans.factory.config.BeanDefinition;

import java.util.List;

/**
 * Bean的实例化类
 */
public class BeanCreater {

    private BeanRegister register;

    public BeanCreater(BeanRegister register) {
        this.register = register;
    }

    public void create(List<BeanDefinition> bds) {
        for (BeanDefinition bd : bds) {
            doCreate(bd);
        }
    }

    private void doCreate(BeanDefinition bd) {
        Object instance = bd.getInstance();
        this.register.registInstanceMapping(bd.getId(), instance);
    }
}
