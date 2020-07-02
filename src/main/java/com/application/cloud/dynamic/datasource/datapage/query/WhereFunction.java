package com.application.cloud.dynamic.datasource.datapage.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author : 孤狼
 * @NAME: WhereFunction
 * @DESC: WhereFunction类设计
 **/
public interface WhereFunction {
	
	/**
	 * 函数实现.
	 * @param wrapper
	 * @param field
	 * @param vaule
	 */
	void whereFunc(QueryWrapper wrapper, String field, Object vaule);
	
}
