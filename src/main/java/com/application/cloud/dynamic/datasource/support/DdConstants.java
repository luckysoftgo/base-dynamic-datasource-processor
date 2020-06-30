
package com.application.cloud.dynamic.datasource.support;

/**
 * 动态数据源常量
 *
 * @author jobob
 * @since 2019-10-08
 */
public interface DdConstants {

    /**
     * 数据源：主库
     */
    String MASTER = "master";
    /**
     * 数据源：从库
     */
    String SLAVE = "slave";

    /**
     * DRUID数据源类
     */
    String DRUID_DATASOURCE = "com.alibaba.druid.pool.DruidDataSource";
    /**
     * HikariCp数据源
     */
    String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";
}
