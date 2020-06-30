
package com.application.cloud.dynamic.datasource.spring.boot.autoconfigure.druid;

import lombok.Data;

/**
 * Druid日志配置
 *
 * @author Lhx
 */
@Data
public class DruidSlf4jConfig {

    private Boolean enable = true;

    private Boolean statementExecutableSqlLogEnable = false;
}