
package com.application.cloud.dynamic.datasource.dataaop;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author TaoYu
 * @since 2.5.0
 */
public class DynamicAspectJExpressionPointcut extends AspectJExpressionPointcut {

    private Map<String, String> matchesCache;

    private String ds;

    public DynamicAspectJExpressionPointcut(String expression, String ds, Map<String, String> matchesCache) {
        this.ds = ds;
        this.matchesCache = matchesCache;
        setExpression(expression);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, boolean beanHasIntroductions) {
        boolean matches = super.matches(method, targetClass, beanHasIntroductions);
        if (matches) {
            matchesCache.put(targetClass.getName() + "." + method.getName(), ds);
        }
        return matches;
    }
}