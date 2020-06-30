
package com.application.cloud.dynamic.datasource;

import com.application.cloud.dynamic.datasource.matcher.ExpressionMatcher;
import com.application.cloud.dynamic.datasource.matcher.Matcher;
import com.application.cloud.dynamic.datasource.matcher.RegexMatcher;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * 基于多种策略的自动切换数据源
 *
 * @author TaoYu
 * @since 2.5.0
 */
public class DynamicDataSourceConfigure {

    @Getter
    private final List<Matcher> matchers = new LinkedList<>();

    private DynamicDataSourceConfigure() {
    }

    public static DynamicDataSourceConfigure config() {
        return new DynamicDataSourceConfigure();
    }

    public DynamicDataSourceConfigure regexMatchers(String pattern, String ds) {
        matchers.add(new RegexMatcher(pattern, ds));
        return this;
    }

    public DynamicDataSourceConfigure expressionMatchers(String expression, String ds) {
        matchers.add(new ExpressionMatcher(expression, ds));
        return this;
    }

}
