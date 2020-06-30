
package com.application.cloud.dynamic.datasource.dataaop;

import com.application.cloud.dynamic.datasource.annotation.DataSourceType;
import com.application.cloud.dynamic.datasource.processor.DsProcessor;
import com.application.cloud.dynamic.datasource.support.DataSourceClassResolver;
import com.application.cloud.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * Core Interceptor of Dynamic Datasource
 *
 * @author TaoYu
 * @since 1.2.0
 */
public class DynamicDataSourceAnnotationInterceptor implements MethodInterceptor {

    /**
     * The identification of SPEL.
     */
    private static final String DYNAMIC_PREFIX = "#";
    private static final DataSourceClassResolver RESOLVER = new DataSourceClassResolver();
    @Setter
    private DsProcessor dsProcessor;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            DynamicDataSourceContextHolder.push(determineDatasource(invocation));
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    private String determineDatasource(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        DataSourceType dataSourceType = method.isAnnotationPresent(DataSourceType.class) ? method.getAnnotation(DataSourceType.class)
                : AnnotationUtils.findAnnotation(RESOLVER.targetClass(invocation), DataSourceType.class);
        String key = dataSourceType.value();
        return (!key.isEmpty() && key.startsWith(DYNAMIC_PREFIX)) ? dsProcessor.determineDatasource(invocation, key) : key;
    }
}