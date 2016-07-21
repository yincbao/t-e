package org.baoting.te.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * ClassName: StringUtil
 * @description
 * @author yin_changbao
 * @Date   Jul 30, 2015
 *
 */
public final class StringUtil {
   
    private StringUtil() {

    }

    public static String firstCharUppercase(String target) {
        if (target == null || "".equals(target)) {
            return target;
        }
        return target.substring(0, 1).toUpperCase().concat(target.substring(1));
    }
    
    public static String getString(Object str) {
        if (str == null || str.getClass().getName() == null) {
            return "";
        } else if ("java.lang.Integer".equals(str.getClass().getName())) {
            return String.valueOf(str);
        } else if ("java.lang.String".equals(str.getClass().getName())) {
            if (str == null || "".equals(str)) {
                return "";
            }
            return ((String) str).trim();
        } else {
            return String.valueOf(str);
        }

    }

    public static Timestamp getTimestamp(String strDate) {
        Date date = null;
		try {
			date = TimeUtil.string2Date(strDate, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }

    /**
     * long时间转格式化时间.
     * 
     * @param time
     *            时间
     * @return 转成对应格式的日前
     */
    public static String longTime2Str(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 截取最后一个指定分隔符到字符串的结束.
     * 
     * @param str
     *            目标字符串
     * @param separator
     *            分隔符
     * @return 结果字符串
     */
    public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return "";
        }

        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == str.length() - separator.length()) {
            return "";
        } else {
            return str.substring(pos + separator.length());
        }
    }

    /**
     * 判断字符串是否为null或空字符串.
     * 
     * @param str
     *            目标字符串
     * @return 判断结果
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 将00或0或空串转为null. 替换原来的方法-> Str00ToNull(String)
     * 
     * @param str
     *            目标字符串
     * @return 如果是0或00或空串将返回null，否则返回原字符串
     */
    public static String strZeroToNull(String str) {
        if (str == null || "".equals(str) || "00".equals(str) || "0".equals(str)) {
            return null;
        }
        return str;
    }

    /**
     * 转成时间字符串.
     * 
     * @param year
     *            年
     * @param hour
     *            小时
     * @param min
     *            分钟
     * @return 转成时间格式
     */
    public static String toTimeString(String year, String hour, String min) {
        if (year == null || "".equals(year)) {
            return null;
        }
        if (hour == null || "".equals(hour)) {
            return year + " " + "00:00:00";
        }
        if (min == null || "".equals(min)) {
            return year + " " + hour + ":00:00";
        }

        return year + " " + hour + ":" + min + ":00";
    }
   /**
    * null to empty 
    * @param value
    * @return
    */
    public static String nullToEmpty(String value){
        if(value == null)
        {
            return "";
        }

        return value;
    }
    
    /**
	 * 两字符串对比.
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return 是否相同
	 */
	public static boolean equals(String str1, String str2)
	{
		if ((str1 == null && str2 == null) || (str1 != null && str1.equals(str2)))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 将字串转成整数，默认返回0
	 * @param intString
	 * @return
	 */
	public static Integer parseInt(String intString) {
		if (intString == null || intString.length() == 0)
			return null;
		try {
			return Integer.parseInt(intString);
		} catch (Exception e) {
		}
		return 0;
	}

	
	/**
	 * 中间空格多个变一个,去字符串首位空格，
	 * @param inputStr
	 * @return
	 */
	public static String trimFormat(String inputStr){
		if (isEmpty(inputStr))
			return "";
		inputStr = inputStr.trim();
		return inputStr.replaceAll("\\s{1,}", " ");
	}
	
}
