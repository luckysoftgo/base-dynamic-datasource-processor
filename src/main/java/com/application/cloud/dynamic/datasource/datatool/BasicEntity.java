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
 * @NAME: BasicEntity
 * @DESC: BasicEntity 类设计
 **/
@Data
@Builder
public class BasicEntity implements Serializable,Cloneable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/** 唯一标识 */
	@TableId
	private Long id = 0L ;
	public static String FIELD_ID = "id";
	
	/** 创建人 */
	private Integer createBy = 0;
	public static String FIELD_CREATE_BY = "createBy";
	
	/** 创建时间 */
	private LocalDateTime createTime = null;
	public static String FIELD_CREATE_TIME = "createTime";
	
	/** 修改人 */
	private Integer updateBy = 0;
	public static String FIELD_UPDATE_BY = "updateBy";
	
	/** 修改时间 */
	private LocalDateTime updateTime = null;
	public static String FIELD_UPDATE_TIME = "updateTime";
	
	public BasicEntity() {
	}
	
	public BasicEntity(Long id,Integer createBy, LocalDateTime createTime, Integer updateBy,
	                   LocalDateTime updateTime) {
		this.id = id;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getCreateBy() {
		return createBy;
	}
	
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	
	public Integer getUpdateBy() {
		return updateBy;
	}
	
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 简易的实体对象.
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getSimpleInstance(Class<T> clazz) {
        BasicEntity entity = null;
        try {
            entity = (BasicEntity) clazz.newInstance();
            return clazz.cast(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	 * 复杂点的实例对象
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getBasicInstance(Class<T> clazz) {
        BasicEntity entity = null;
        try {
            entity = (BasicEntity) clazz.newInstance();
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
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
    public String getInformations(BasicEntity entity) {
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