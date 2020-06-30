
package com.application.cloud.dynamic.datasource.matcher;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author TaoYu
 * @since 2.5.0
 */
@AllArgsConstructor
@Data
public class RegexMatcher implements Matcher {

    private String pattern;

    private String ds;
}

