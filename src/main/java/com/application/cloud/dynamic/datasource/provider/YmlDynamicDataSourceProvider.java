
package com.application.cloud.dynamic.datasource.provider;

import com.application.cloud.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Map;

/**
 * YML数据源提供者
 *
 * @author TaoYu Kanyuxia
 * @since 1.0.0
 */
@Slf4j
@AllArgsConstructor
public class YmlDynamicDataSourceProvider extends AbstractDataSourceProvider implements DynamicDataSourceProvider {

    /**
     * 所有数据源
     */
    private Map<String, DataSourceProperty> dataSourcePropertiesMap;

    @Override
    public Map<String, DataSource> loadDataSources() {
        return createDataSourceMap(dataSourcePropertiesMap);
    }
}
