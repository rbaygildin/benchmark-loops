package org.rbaygildin.infrastructure.bpp;

import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.rbaygildin.benchmark.IMeasurer;
import org.rbaygildin.infrastructure.annotation.Benchmark;
import org.rbaygildin.infrastructure.annotation.MeasureDisabled;
import org.rbaygildin.infrastructure.annotation.TestMethod;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Настраивает для бинов замер времени исполнения их методов
 *
 * @author Ярных Роман (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see IMeasurer
 * @see Benchmark
 * @see MeasureDisabled
 * @see TestMethod
 */
@Component
public class BenchmarkBPP implements BeanPostProcessor, Ordered
{

    @Autowired
    private ApplicationContext context;

    private Map<String, Object> beanMap_ = new HashMap<>();

    public int getOrder()
    {
        return HIGHEST_PRECEDENCE;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
    {
        if(bean.getClass().isAnnotationPresent(Benchmark.class))
        {
            beanMap_.put(beanName, bean);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException
    {
        Object o = beanMap_.get(beanName);
        if (o != null)
        {
            Class beanClazz_ = o.getClass();
            Benchmark benchmarkAnn = (Benchmark) beanClazz_.getAnnotation(Benchmark.class);
            IMeasurer measurer;
            if(!benchmarkAnn.measurerName().isEmpty())
                measurer = (IMeasurer) context.getBean(benchmarkAnn.measurerName());
            else
                measurer = context.getBean(benchmarkAnn.measurer());
            if(!measurer.isEnabled())
                return bean;
            return Proxy.newProxyInstance(beanClazz_.getClassLoader(),
                    beanClazz_.getInterfaces(),
                    (proxy, method, args) -> {
                        Method origMethod = beanClazz_.getMethod(method.getName(), method.getParameterTypes());
                        if(!origMethod.isAnnotationPresent(MeasureDisabled.class) && measurer.isEnabled()){
                            return measurer.measure(o, origMethod, args);
                        }
                        else
                            return origMethod.invoke(o, args);
                    });
        }
        return bean;
    }
}
