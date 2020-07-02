package com.application.cloud.dynamic.datasource.datapage.query;

import com.application.cloud.dynamic.datasource.datapage.BasicPageVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: QueryWhereProcessor
 * @DESC: 自定义查询处理类
 **/
@Data
public class QueryWhereProcessor<T> {
	
	/**
	 * 类型描述
	 */
	private static Map<WhereTypeEnum, WhereFunction> typeFunc;
	
	/**
	 * 初始化设置.
	 */
	static {
		if (typeFunc == null) {
			typeFunc = new HashMap<>();
			typeFunc.put(WhereTypeEnum.EQ, (w, k, v) ->
				{w.eq(k, v);
			});
			typeFunc.put(WhereTypeEnum.NEQ, (w, k, v) -> {
				w.ne(k, v);
			});
			typeFunc.put(WhereTypeEnum.IN, (w, k, v) -> {
				if (v instanceof Collection) {
					w.in(k, (Collection<?>) v);
				} else if (v instanceof Object[]) {
					w.in(k, (Object[]) v);
				} else {
					w.in(k, v.toString());
				}
			});
			typeFunc.put(WhereTypeEnum.LIKE, (w, k, v) -> {
				w.like(k, v.toString());
			});
			typeFunc.put(WhereTypeEnum.LE, (w, k, v) -> {
				w.le(k, v);
			});
			typeFunc.put(WhereTypeEnum.LT, (w, k, v) -> {
				w.lt(k, v);
			});
			typeFunc.put(WhereTypeEnum.GE, (w, k, v) -> {
				w.ge(k, v);
			});
			typeFunc.put(WhereTypeEnum.GT, (w, k, v) -> {
				w.gt(k, v);
			});
		}
	}
	
	/**
	 * 封装成需要的wrapper
	 *
	 * @param object 实体对象
	 * @return
	 */
	public static Wrapper invoke(Object object) {
		QueryWrapper wrapper = new QueryWrapper();
		excute(object, wrapper);
		// 获取
		return wrapper;
	}
	
	/**
	 * 封装成需要的wrapper
	 *
	 * @param object 实体对象
	 * @return
	 */
	public static Wrapper invoke(Object object,String... columns) {
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.select(columns);
		excute(object, wrapper);
		// 获取
		return wrapper;
	}
	
	
	/**
	 * 执行
	 *
	 * @param t       obj
	 * @param wrapper
	 */
	public static void excute(Object t, QueryWrapper wrapper) {
		//反射获取属性
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object val = field.get(t);
				String colum = "";
				if (val != null && !"".equals(val.toString())) {
					QueryCondition whereType = field.getAnnotation(QueryCondition.class);
					//没有注解，取默认为下划线拼接
					if (whereType == null) {
						colum = camelToUnderline(field.getName());
						// 执行方法
						typeFunc.get(WhereTypeEnum.EQ).whereFunc(wrapper, colum, val);
					} else {
						if (whereType.ignore()) {
							continue;
						} else {
							//没有定义查询属性，取默认
							if (StringUtils.isNotBlank(whereType.filed())) {
								colum = whereType.filed();
							} else {
								colum = camelToUnderline(field.getName());
							}
							// 是否另取一个where
							if (whereType.andNew()) {
								//wrapper.andNew();
							}
							// 执行方法
							typeFunc.get(whereType.type()).whereFunc(wrapper, colum, val);
						}
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 驼峰转下划线
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append("_");
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		BasicPageVO pageVO = new BasicPageVO();
		pageVO.setPageNum("1");
		pageVO.setPageSize("10");
		Wrapper<BasicPageVO> wrapper1 = QueryWhereProcessor.invoke(pageVO);
		System.out.println(wrapper1.getTargetSql()+wrapper1.getSqlSelect());
		Wrapper<BasicPageVO> wrapper2 = QueryWhereProcessor.invoke(pageVO,new String[]{"pageNum","pageSize"});
		System.out.println(wrapper2.getTargetSql()+wrapper2.getSqlSelect());
	}

}
