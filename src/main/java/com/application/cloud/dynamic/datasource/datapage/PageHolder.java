package com.application.cloud.dynamic.datasource.datapage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 孤狼
 * @NAME: PageQuery
 * @DESC: Query类设计
 **/
public class PageHolder<T> implements Serializable {
	
	/**
	 * 分页设置
	 * @param params
	 * @return
	 */
	public IPage<T> getQueryPage(Map<String,Object> params) {
		return this.getQueryPage(params, null, false);
	}
	
	/**
	 * 获得分页设置.
	 * @param params : 参数.
	 * @param defaultOrderFields : 排序字段.
	 * @param isAsc : 是否是 asc 排序模式.
	 * @return
	 */
	public IPage<T> getQueryPage(Map<String,Object> params, String[] defaultOrderFields, boolean isAsc) {
		//分页参数默认值
		Long currentPage = 1L;
		Long pageSize = 10L;
		if(params.get(Constant.PAGE_SIZE) != null){
			pageSize = Long.parseLong(Objects.toString(params.get(Constant.PAGE_SIZE),"0"));
		}
		if(params.get(Constant.PAGE_NUM) != null){
			currentPage = Long.parseLong(Objects.toString(params.get(Constant.PAGE_NUM),"0"));
		}
		if(params.get(Constant.MYBATIS_PLUS_LIMIT) != null){
			pageSize = Long.parseLong(Objects.toString(params.get(Constant.MYBATIS_PLUS_LIMIT),"0"));
		}
		if(params.get(Constant.MYBATIS_PLUS_PAGE) != null){
			currentPage = Long.parseLong(Objects.toString(params.get(Constant.MYBATIS_PLUS_PAGE),"0"));
		}
		//分页对象
		Page<T> page = new Page<>(currentPage, pageSize);
		//防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
		String orderField = SQLFilter.sqlInject(Objects.toString(params.get(Constant.ORDER_FIELD),""));
		String order = Objects.toString(params.get(Constant.ORDER_BY),"");
		return getPage(defaultOrderFields, isAsc, page, orderField, order);
	}
	
	
	/**
	 * 分页设置
	 * @param params
	 * @return
	 */
	public IPage<T> getQueryPage(BasicPageVO params) {
		return this.getQueryPage(params, null, false);
	}
	
	/**
	 * 获得分页设置.
	 * @param params : 参数.
	 * @param defaultOrderFields : 排序字段.
	 * @param isAsc : 是否是 asc 排序模式.
	 * @return
	 */
	public IPage<T> getQueryPage(BasicPageVO params, String[] defaultOrderFields, boolean isAsc) {
		//分页参数默认值
		Long currentPage = 1L;
		Long pageSize = 10L;
		if(StringUtils.isNotEmpty(params.getPageSize())){
			pageSize = Long.parseLong(params.getPageSize());
		}
		if(StringUtils.isNotEmpty(params.getPageNum())){
			currentPage = Long.parseLong(params.getPageNum());
		}
		if(StringUtils.isNotEmpty(params.getLimit())){
			pageSize = Long.parseLong(params.getLimit());
		}
		if(StringUtils.isNotEmpty(params.getPage())){
			currentPage = Long.parseLong(params.getPage());
		}
		//分页对象
		Page<T> page = new Page<>(currentPage, pageSize);
		//防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
		String orderField = SQLFilter.sqlInject(params.getOrderField());
		String order = Objects.toString(params.getOrderBy());
		//前端字段排序
		return getPage(defaultOrderFields, isAsc, page, orderField, order);
	}
	
	/**
	 * 构建
	 * @param defaultOrderFields
	 * @param isAsc
	 * @param page
	 * @param orderField
	 * @param order
	 * @return
	 */
	private IPage<T> getPage(String[] defaultOrderFields, boolean isAsc, Page<T> page, String orderField,
	                         String order) {
		//前端字段排序
		if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
			if (Constant.ASC.equalsIgnoreCase(order)) {
				return page.addOrder(OrderItem.ascs(orderField));
			} else {
				return page.addOrder(OrderItem.descs(orderField));
			}
		}
		//没有排序字段，则不排序
		if (defaultOrderFields == null || defaultOrderFields.length == 0) {
			return page;
		}
		//默认排序
		if (isAsc) {
			page.addOrder(OrderItem.ascs(defaultOrderFields));
		} else {
			page.addOrder(OrderItem.descs(defaultOrderFields));
		}
		return page;
	}
}