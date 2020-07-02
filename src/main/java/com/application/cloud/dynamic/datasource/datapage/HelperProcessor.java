package com.application.cloud.dynamic.datasource.datapage;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.List;
/**
 * @author : 孤狼
 * @NAME: HelperProcessor
 * @DESC: PageInfoProcessor类设计
 **/
@ApiModel(description="pagehelper分页返回结果集")
public class HelperProcessor<T> {
	
	@ApiModelProperty(value = "当前页")
	private int currentPage;
	@ApiModelProperty(value = "每页的数量")
	private int pageSize;
	@ApiModelProperty(value = "当前页的数量")
	private int currentSize;
	/**
	 * 由于startRow和endRow不常用，这里说个具体的用法
	 * 可以在页面中"显示startRow到endRow 共size条数据"
	 */
	@ApiModelProperty(value = "当前页面第一个元素在数据库中的行号")
	private int startRow;
	@ApiModelProperty(value = "当前页面最后一个元素在数据库中的行号")
	private int endRow;
	@ApiModelProperty(value = "总页数")
	private int totalPage;
	@ApiModelProperty(value = "前一页")
	private int prePage;
	@ApiModelProperty(value = "下一页")
	private int nextPage;
	@ApiModelProperty(value = "是否为第一页")
	private boolean firstPage = false;
	@ApiModelProperty(value = "是否为最后一页")
	private boolean lastPage = false;
	@ApiModelProperty(value = "是否有前一页")
	private boolean hasPreviousPage = false;
	@ApiModelProperty(value = "是否有下一页")
	private boolean hasNextPage = false;
	@ApiModelProperty(value = "导航页码数")
	private int navigatePages;
	@ApiModelProperty(value = "所有导航页号")
	private int[] navigatepageNums;
	@ApiModelProperty(value = "导航条上的第一页")
	private int navigateFirstPage;
	@ApiModelProperty(value = "导航条上的最后一页")
	private int navigateLastPage;
	@ApiModelProperty(value = "总记录数")
	private long totalCount;
	@ApiModelProperty(value = "结果列表数据")
	private List<T> dataList;
	
	/**
	 * 构造函数
	 */
	public HelperProcessor() {
		this.firstPage = false;
		this.lastPage = false;
		this.hasPreviousPage = false;
		this.hasNextPage = false;
	}
	
	/**
	 * 构造函数
	 * @param list
	 */
	public HelperProcessor(List<T> list) {
		this(list, 8);
		this.dataList = list;
		if (list instanceof Page) {
			this.totalCount = ((Page) list).getTotal();
		} else {
			this.totalCount = (long) list.size();
		}
	}
	
	/**
	 * 构造函数
	 * @param list
	 */
	public HelperProcessor(List<T> list, int navigatePages) {
		this.firstPage = false;
		this.lastPage = false;
		this.hasPreviousPage = false;
		this.hasNextPage = false;
		if (list instanceof Page) {
			Page page = (Page) list;
			this.currentPage = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.totalPage = page.getPages();
			this.currentSize = page.size();
			if (this.currentSize == 0) {
				this.startRow = 0;
				this.endRow = 0;
			} else {
				this.startRow = page.getStartRow() + 1;
				this.endRow = this.startRow - 1 + this.currentSize;
			}
		} else if (list instanceof Collection) {
			this.currentPage = 1;
			this.pageSize = list.size();
			this.totalPage = this.pageSize > 0 ? 1 : 0;
			this.currentSize = list.size();
			this.startRow = 0;
			this.endRow = list.size() > 0 ? list.size() - 1 : 0;
		}
		
		if (list instanceof Collection) {
			this.navigatePages = navigatePages;
			this.calcNavigatepageNums();
			this.calcPage();
			this.judgePageBoudary();
		}
		
	}
	
	/**
	 * 当前页.
	 */
	private void calcNavigatepageNums() {
		//当总页数小于或等于导航页码数时
		if (totalPage <= navigatePages) {
			navigatepageNums = new int[totalPage];
			for (int i = 0; i < totalPage; i++) {
				navigatepageNums[i] = i + 1;
			}
		} else { //当总页数大于导航页码数时
			navigatepageNums = new int[navigatePages];
			int startNum = currentPage - navigatePages / 2;
			int endNum = currentPage + navigatePages / 2;
			if (startNum < 1) {
				startNum = 1;
				//(最前navigatePages页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			} else if (endNum > totalPage) {
				endNum = totalPage;
				//最后navigatePages页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatepageNums[i] = endNum--;
				}
			} else {
				//所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			}
		}
	}
	
	/**
	 * 计算页
	 */
	private void calcPage() {
		if (this.navigatepageNums != null && this.navigatepageNums.length > 0) {
			this.navigateFirstPage = this.navigatepageNums[0];
			this.navigateLastPage = this.navigatepageNums[this.navigatepageNums.length - 1];
			if (this.currentPage > 1) {
				this.prePage = this.currentPage - 1;
			}
			if (this.currentPage < this.totalPage) {
				this.nextPage = this.currentPage + 1;
			}
		}
	}
	
	/**
	 * 判断
	 */
	private void judgePageBoudary() {
		this.firstPage = this.currentPage == 1;
		this.lastPage = this.currentPage == this.totalPage || this.totalPage == 0;
		this.hasPreviousPage = this.currentPage > 1;
		this.hasNextPage = this.currentPage < this.totalPage;
	}
	
}
