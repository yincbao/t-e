/*****************************************************************************
 *
 *                      HOPERUN PROPRIETARY INFORMATION
 *
 *          The information contained herein is proprietary to HopeRun
 *           and shall not be reproduced or disclosed in whole or in part
 *                    or used for any design or manufacture
 *              without direct written authorization from HopeRun.
 *
 *            Copyright (coffee) 2015 by HopeRun.  All rights reserved.
 *
 *****************************************************************************/
package org.baoting.te.utils;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: TimePeriodFilter
 * @description
 * @author xu_hongli
 * @Date   Jan 14, 2016
 * 
 */
public class TimePeriodFilter implements FilenameFilter {
	private String firstHalf;
	private String ext;
	
	public TimePeriodFilter(String firstHalf, String ext){
		this.firstHalf = firstHalf;
		this.ext = ext;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		if(StringUtils.isEmpty(firstHalf) && StringUtils.isEmpty(ext))
			return true;
		if(StringUtils.isEmpty(firstHalf))
			return name.endsWith("."+ext);
		if(StringUtils.isEmpty(ext))
			return name.startsWith(firstHalf);
		return name.startsWith(firstHalf) && name.endsWith("."+ext);
	}
}
