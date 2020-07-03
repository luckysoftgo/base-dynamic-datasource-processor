package com.application.cloud.dynamic.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : 孤狼
 * @NAME: DynamicProperties
 * @DESC: DynamicProperties类设计
 **/
@Data
@ConfigurationProperties(prefix = "dynamic.config")
public class DynamicProperties {
	
	/**
	 * 是否打开API文档,默认关闭
	 */
	private Boolean swaggerUiOpen=false;
	
	/**
	 * 允许访问的ip
	 */
	private String druidAllowIp;
	/**
	 * 禁止访问的ip
	 */
	private String druidDenyIp;
	
	/**
	 * 用户名
	 */
	private String druidName;
	
	/**
	 * 密码
	 */
	private String druidPass;
	
	
}
