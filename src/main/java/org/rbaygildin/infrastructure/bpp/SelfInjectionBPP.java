package org.rbaygildin.infrastructure.bpp;

import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.rbaygildin.infrastructure.annotation.SelfInject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Впрыскивает в поле, помеченное {@linkplain SelfInject}, текущего бина ссылку на прокси-объект,
 * связанного с этим бином и полученного из него путем проксирования
 *
 * @author Ярных Роман (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see SelfInject
 */
@Component
public class SelfInjectionBPP implements BeanPostProcessor, Ordered{

    private Map<String, Object> beanMap_ = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClazz = bean.getClass();
        for(Field field : beanClazz.getDeclaredFields()){
            if(field.isAnnotationPresent(SelfInject.class)){
                beanMap_.put(beanName, bean);
                break;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object origBean = beanMap_.get(beanName);
        if (origBean != null) {
            for(Field field : origBean.getClass().getDeclaredFields()){
                if(field.isAnnotationPresent(SelfInject.class)){
                    field.setAccessible(true);
                    try {
                        field.set(origBean, bean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
