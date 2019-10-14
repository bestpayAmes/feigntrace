package com.qihoo.finance.msf;

import com.qihoo.finance.msf.register.RegisterService;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class TraceGraph implements BeanPostProcessor {

    @Value("${spring.application.name}")
    private String currentSys;


    @Autowired
    private RegisterService registerService;

    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {

        Class<?> objClz;
        if (AopUtils.isAopProxy(bean)) {
            objClz = AopUtils.getTargetClass(bean);
        } else {
            objClz = bean.getClass();
        }

        try {
            for (Field field : objClz.getDeclaredFields()) {
                field.setAccessible(true);
                Object object = field.get(bean);
                if (object!=null&&object.toString()!=null&&object.toString().contains("HardCodedTarget")) {
                    registerService.registerAppOfServiceBeUsed(field.getType().getName(), currentSys);
                    registerService.registerServiceOfAppUse(currentSys, field.getType().getName());
                }

            }
        } catch (Exception e) {
        }
        return bean;
    }
}
