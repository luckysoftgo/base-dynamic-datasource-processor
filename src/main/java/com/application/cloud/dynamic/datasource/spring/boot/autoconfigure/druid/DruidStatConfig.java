
package com.application.cloud.dynamic.datasource.spring.boot.autoconfigure.druid;

import lombok.Data;

/**
 * Druid监控配置
 *
 * @author TaoYu
 */
@Data
public class DruidStatConfig {

    private Long slowSqlMillis;

    private Boolean logSlowSql;

    private Boolean mergeSql;
}