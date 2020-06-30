
package com.application.cloud.dynamic.datasource.creator;

import com.application.cloud.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.application.cloud.dynamic.datasource.spring.boot.autoconfigure.hikari.HikariCpConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * Hikari数据源创建器
 *
 * @author TaoYu
 * @since 2020/1/21
 */
@Data
@AllArgsConstructor
public class HikariDataSourceCreator {

    private HikariCpConfig hikariCpConfig;

    public DataSource createDataSource(DataSourceProperty dataSourceProperty) {
        HikariConfig config = dataSourceProperty.getHikari().toHikariConfig(hikariCpConfig);
        config.setUsername(dataSourceProperty.getUsername());
        config.setPassword(dataSourceProperty.getPassword());
        config.setJdbcUrl(dataSourceProperty.getUrl());
        config.setPoolName(dataSourceProperty.getPoolName());
        String driverClassName = dataSourceProperty.getDriverClassName();
        if(!StringUtils.isEmpty(driverClassName)){
            config.setDriverClassName(driverClassName);
        }
        return new HikariDataSource(config);
    }
}
