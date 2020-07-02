package com.application.cloud.dynamic.datasource.datapage;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: BasicPageVO
 * @DESC: BasicPageVo类设计
 **/
@Data
public class BasicPageVO implements Serializable {
	
	/**
	 * 当前页码
	 */
	private String pageNum;
	/**
	 * 每页显示记录数
	 */
	private String pageSize;
	
	/**
	 * mybatis-plus 需要的参数
	 */
	private String page;
	/**
	 * mybatis-plus 需要的参数
	 */
	private String limit;
	/**
	 * mybatis-plus 需要的参数
	 */
	private String offset;
	/**
	 * mybatis-plus 需要的参数
	 */
	private String sidx;
	/**
	 * mybatis-plus 需要的参数
	 */
	private String order;
	
	/**
	 * 排序字段
	 */
	private String orderField;
	
	/**
	 * 排序方式
	 */
	private String orderBy;
	
	/**
	 *  升序
	 */
	private String asc;
	
}
