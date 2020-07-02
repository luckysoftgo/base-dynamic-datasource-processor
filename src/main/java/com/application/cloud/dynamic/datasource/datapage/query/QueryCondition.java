package com.application.cloud.dynamic.datasource.datapage.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 孤狼
 * @NAME: QueryCondition
 * @DESC: QueryCondition类设计
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryCondition {
	
	/**
	 * <p>
	 * 查询类型
	 * </p>
	 */
	WhereTypeEnum type() default WhereTypeEnum.EQ;
	
	/**
	 * 判定字段名
	 * @return
	 */
	String filed() default "";
	
	/**
	 * 是否忽略
	 */
	boolean ignore() default false;
	
	/**
	 * 是否新增
	 * @return
	 */
	boolean andNew() default  false;

}
