package com.application.cloud.dynamic.datasource.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : 孤狼
 * @NAME: MybatisPageConfig
 * @DESC: MybatisPageConfig类设计
 **/
@Configuration
@EnableConfigurationProperties(DynamicProperties.class)
public class DataSourceXConfig {
	
	@Autowired
	private DynamicProperties properties;
	
	/**
	 * DateTime格式化字符串
	 */
	private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * Date格式化字符串
	 */
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * Time格式化字符串
	 */
	private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return builder -> builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)))
				.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)))
				.serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)))
				.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)))
				.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)))
				.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
	}
	
	@Bean
	@Primary
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule timeimeModule = new JavaTimeModule();
		timeimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)));
		timeimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
		timeimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
		objectMapper.registerModule(timeimeModule);
		return objectMapper;
	}
	
	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
	
	/**
	 * druid 访问配置.
	 * @return
	 */
	@Bean
	public ServletRegistrationBean druidStatViewServlet() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		// IP白名单 (没有配置或者为空，则允许所有访问)
		if (StringUtils.isEmpty(properties.getDruidAllowIp())){
			registrationBean.addInitParameter("allow", "");
		}else {
			registrationBean.addInitParameter("allow", properties.getDruidAllowIp());
		}
		// IP黑名单 (存在共同时，deny优先于allow)
		if (StringUtils.isEmpty(properties.getDruidDenyIp())){
			registrationBean.addInitParameter("deny", "");
		}else {
			registrationBean.addInitParameter("deny", properties.getDruidDenyIp());
		}
		//用户名/密码
		if (StringUtils.isEmpty(properties.getDruidName())){
			registrationBean.addInitParameter("loginUsername", "admin");
			registrationBean.addInitParameter("loginPassword", "druid");
		}else{
			registrationBean.addInitParameter("loginUsername", properties.getDruidName());
			registrationBean.addInitParameter("loginPassword", properties.getDruidPass());
		}
		//禁用HTML页面上的“Reset All”功能
		registrationBean.addInitParameter("resetEnable", "false");
		return registrationBean;
	}
	
	/**
	 * druid 资源配置.
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidWebStatViewFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter());
		registrationBean.addInitParameter("urlPatterns", "/*");
		registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		return registrationBean;
	}
	
	/**
	 * 去除监控页面底部的广告
	 */
	/*
	@Bean
	public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties){
		// 获取web监控页面的参数
		DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
		// 提取common.js的配置路径
		String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
		final String filePath = "support/http/resources/js/common.js";
		// 创建filter进行过滤
		Filter filter = new Filter(){
			@Override
			public void init(javax.servlet.FilterConfig filterConfig) throws ServletException{
			}
			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException{
				chain.doFilter(request, response);
				// 重置缓冲区，响应头不会被重置
				response.resetBuffer();
				// 获取common.js
				String text = Utils.readFromResource(filePath);
				// 正则替换banner, 除去底部的广告信息
				text = text.replaceAll("<a.*?banner\"></a><br/>", "");
				text = text.replaceAll("powered.*?shrek.wang</a>", "");
				response.getWriter().write(text);
			}
			
			@Override
			public void destroy(){}
		};
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns(commonJsPattern);
		return registrationBean;
	}
	*/
}
