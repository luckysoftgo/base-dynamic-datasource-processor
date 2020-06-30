package com.application.cloud.dynamic.datasource.datatool;

/**
 * 常量数据类
 * 
 * @ClassName: Constants
 */
public class Constants {

	/**
     * 换行符
     */
    @SuppressWarnings("restriction")
	public static String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * java包装类型的扩展常量
	 */
	public interface WrapperExtend {
		Integer ZERO = 0;
		String EMPTY = "";
	}

	/**
	 * 分隔符
	 */
	public interface Separator {
		/**
		 * 表名后缀
		 */
		String TABLE_SUFFIX = "_T";
		/**
		 * 分隔符'_'
		 */
		String UPDERLINE = "_";
		/**
		 * 逗号
		 */
		String COMMA = ",";
		/**
		 * 反斜杠
		 */
		String BACKSLASH = "\\";
		/**
		 * 斜杠
		 */
		String SLASH = "/";
		/**
		 * 点
		 */
		String DOT = ".";
		/**
		 * 回车换行
		 */
		String CRLF = "\r\n";
		/**
		 * 单引号
		 */
		String SINGLE_QUOTES = "'";
		/**
		 * 双引号
		 */
		String SINGLE_DOUBLEQUOTES = "\"";
		/**
		 * 叹号
		 */
		String EXCLAMATION_MARK = "!";
		/**
		 * 空格
		 */
		String BLANK = " ";
		/**
		 * 百分号
		 */
		String PERCENT = "%";
		/**
		 * 两位小数
		 */
		String TWO_DIGITS_DECIMALS = "#.00";
		/**
		 * 连接符
		 */
		String CONNECTOR = "-";
		/**
		 * 顿号
		 */
		String PAUSE = "、";
		/**
		 * 左括号
		 */
		String LBRACKET = "(";
		/**
		 * 右括号
		 */
		String RBRACKET = ")";
		/**
		 * 冒号
		 */
		String COLON = ":";
		/**
		 * 问号
		 */
		String QUESTION_MARK = "?";
		/**
		 * 等号
		 */
	    String EQUAL_MARK = "=";
	    /**
	     * 与号
	     */
	    String AND_MARK = "&";
	    /**
	     * 斜线
	     */
	    String LINE_SEPARATOR = System.getProperty("line.separator");
	}

	/**
	 * 文件后缀类型
	 */
	public interface FileType {
		/**
		 * xml后缀
		 */
		String XML_SUFFIX = ".xml";
		/**
		 * java后缀
		 */
		String JAVA_SUFFIX = ".java";
		/**
		 * jsp后缀
		 */
		String JSP_SUFFIX = ".jsp";
		/**
		 * html后缀
		 */
		String HTML_SUFFIX = ".html";
	}

	/**
	 * API调用返回状态
	 */
	public interface APICALL {
		/**
		 * 调用成功.
		 */
		String API_CALL_SYS_SUCCESS = "200";
		/**
		 * 服务器内部错误.
		 */
		String API_CALL_SYS_ERROR = "500"; 
		/**
		 * 找不到资源.
		 */
		String API_CALL_IS_NULL = "404";
	}

	/**
	 * 是否删除
	 */
	public interface OperateStatus {
		// 所有禁用为1
		Integer DISABLED = 1;
		// 所有启用为0
		Integer ENABLED = 0;
	}

	/**
	 * 缓存定义
	 */
	public interface CacheBuff {
		String TABLE = "TABLE";
		String ROW_CACHE = "ROW_CACHE";
		String TABLE_CACHE = "TABLE_CACHE";
		String DATABASE_CACHE = "DATABASE_CACHE";
	}

	/**
	 * 字符集定义
	 */
	public interface CharSet {
		String UTF8 = "UTF-8";
		String GBK = "GBK";
	}
	
}
