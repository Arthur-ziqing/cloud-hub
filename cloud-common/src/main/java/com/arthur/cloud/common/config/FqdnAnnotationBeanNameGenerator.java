package com.arthur.cloud.common.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.beans.Introspector;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 加载bean
 * @create 2020-04-03 10:42
 * @Version 1.0
 **/
public class FqdnAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    public FqdnAnnotationBeanNameGenerator(){

    }

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition){
        String fqdn = definition.getBeanClassName();
        try {
            Class clz = Class.forName(fqdn);
            if (clz.getAnnotation(SpringBootApplication.class) != null){
                fqdn = "application";
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return Introspector.decapitalize(fqdn);
    }
}
