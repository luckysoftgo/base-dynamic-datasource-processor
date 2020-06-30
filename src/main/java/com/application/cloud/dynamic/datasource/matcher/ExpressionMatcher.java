
package com.application.cloud.dynamic.datasource.matcher;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author TaoYu
 * @since 2.5.0
 */
@AllArgsConstructor
@Data
public class ExpressionMatcher implements Matcher {

    private String expression;

    private String ds;
}