
package com.application.cloud.dynamic.datasource.spring.boot.autoconfigure;

import com.application.cloud.dynamic.datasource.creator.BasicDataSourceCreator;
import com.application.cloud.dynamic.datasource.creator.DataSourceCreator;
import com.application.cloud.dynamic.datasource.creator.DruidDataSourceCreator;
import com.application.cloud.dynamic.datasource.creator.HikariDataSourceCreator;
import com.application.cloud.dynamic.datasource.creator.JndiDataSourceCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceCreatorAutoConfiguration {

    private final DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DataSourceCreator dataSourceCreator() {
        DataSourceCreator dataSourceCreator = new DataSourceCreator();
        dataSourceCreator.setBasicDataSourceCreator(basicDataSourceCreator());
        dataSourceCreator.setJndiDataSourceCreator(jndiDataSourceCreator());
        dataSourceCreator.setDruidDataSourceCreator(druidDataSourceCreator());
        dataSourceCreator.setHikariDataSourceCreator(hikariDataSourceCreator());
        dataSourceCreator.setGlobalPublicKey(properties.getPublicKey());
        return dataSourceCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    public BasicDataSourceCreator basicDataSourceCreator() {
        return new BasicDataSourceCreator();
    }

    @Bean
    @ConditionalOnMissingBean
    public JndiDataSourceCreator jndiDataSourceCreator() {
        return new JndiDataSourceCreator();
    }

    @Bean
    @ConditionalOnMissingBean
    public DruidDataSourceCreator druidDataSourceCreator() {
        return new DruidDataSourceCreator(properties.getDruid());
    }

    @Bean
    @ConditionalOnMissingBean
    public HikariDataSourceCreator hikariDataSourceCreator() {
        return new HikariDataSourceCreator(properties.getHikari());
    }
}
