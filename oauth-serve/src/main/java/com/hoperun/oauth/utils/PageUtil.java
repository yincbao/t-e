package com.hoperun.oauth.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName PageUtil.java
 * @Description
 * @author yin_changbao
 * @Date Jun 2, 2016 11:49:53 AM
 *
 */
public class PageUtil {

	public static int PAGE_SIZE = 10;

	/**
	 * 
	 * @param page
	 * @param request
	 * @return int[], length is 2 and [currentPage,pageSize]
	 */
	public static<T> void init(Page<T> page, HttpServletRequest request) {
		if(page==null)
			page = new Page<T>();
		int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("p"), "1"));
		page.setPageNo(pageNumber);
		int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("ps"), String.valueOf(PAGE_SIZE)));
		page.setPageSize(pageSize);
	}

	public static<T> int[] parsePageNums(Page<T> page) {
		if(page==null)
			page = new Page<T>();
		int firstResult = page.getFirst() - 1;
		int maxResults = page.getPageSize();
		return new int[] { firstResult, maxResults };
	}
}
