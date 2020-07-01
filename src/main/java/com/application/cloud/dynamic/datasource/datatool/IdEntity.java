package com.application.cloud.dynamic.datasource.datatool;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author : 孤狼
 * @NAME: IdEntity
 * @DESC: IdEntity类设计
 **/
@Data
@Builder
public class IdEntity implements Serializable,Cloneable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/** 唯一标识 */
	@TableId
	private Long id = 0L ;
	public static String FIELD_ID = "id";
	
	public IdEntity() {
	}
	
	public IdEntity(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 简易的实体对象.
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getSimpleInstance(Class<T> clazz) {
		IdEntity entity = null;
		try {
			entity = (IdEntity) clazz.newInstance();
			return clazz.cast(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取基本信息.
	 * @param entity
	 * @return
	 */
	public String getInformations(GenericEntity entity) {
		StringBuffer buffer = new StringBuffer(entity.getClass().getName() + ",infos : \n");
		try {
			Class<?> cls1 = entity.getClass();
			Field[] fields1 = cls1.getDeclaredFields();
			Class<?> cls2 = entity.getClass().getSuperclass();
			Field[] fields2 = cls2.getDeclaredFields();
			// 添加集合中去.
			ArrayList<Field> fields = new ArrayList<Field>();
			fields.addAll(Arrays.asList(fields1));
			fields.addAll(Arrays.asList(fields2));
			// 排查...
			Integer index = 0;
			String name = null;
			for (Field field : fields) {
				name = field.getName();
				index++;
				field.setAccessible(true);
				Object val = field.get(entity);
				if (name.startsWith("serial") || name.startsWith("FIELD") || name.startsWith("tableName")
						|| name.startsWith("orderBy")) {
					continue;
				}
				if (name.startsWith("createTime") || name.startsWith("updateTime") || name.startsWith("createDate")
						|| name.startsWith("updateDate")) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					if (val != null) {
						val = ((LocalDateTime) val).format(formatter);
					}
				}
				if (index == fields.size() - 1) {
					buffer.append(name + ":" + val);
				}
				else {
					buffer.append(name + ":" + val + ",");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
