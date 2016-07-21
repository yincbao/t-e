package org.baoting.te.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @ClassName IsmUtil.java
 * @Description 
 * @author yin_changbao
 * @Date Jul 21, 2016 1:56:27 PM
 *
 */
public class TeUtil {
	
	private static final Log logger = LogFactory.getLog(TeUtil.class);

	public static long[] arrayAppend(long[] prefix, long[] sfix) {
		long[] tmp = null;
		long[] result = null;
		if ((prefix != null && prefix.length > 0)) {
			tmp = new long[prefix.length];
			System.arraycopy(prefix, 0, tmp, 0, prefix.length);
		}
		if ((sfix != null && sfix.length > 0)) {
			if (tmp == null) {
				tmp = new long[sfix.length];
				System.arraycopy(sfix, 0, tmp, 0, sfix.length);
				result = tmp;
			} else {
				result = new long[tmp.length + sfix.length];
				System.arraycopy(tmp, 0, result, 0, tmp.length);
				System.arraycopy(sfix, 0, result, tmp.length, sfix.length);
			}

		} else
			result = tmp;

		return result;
	}

	public static byte[] arrayAppend(byte[] prefix, byte[] sfix) {
		byte[] tmp = null;
		byte[] result = null;
		if ((prefix != null && prefix.length > 0)) {
			tmp = new byte[prefix.length];
			System.arraycopy(prefix, 0, tmp, 0, prefix.length);
		}
		if ((sfix != null && sfix.length > 0)) {
			if (tmp == null) {
				tmp = new byte[sfix.length];
				System.arraycopy(sfix, 0, tmp, 0, sfix.length);
				result = tmp;
			} else {
				result = new byte[tmp.length + sfix.length];
				System.arraycopy(tmp, 0, result, 0, tmp.length);
				System.arraycopy(sfix, 0, result, tmp.length, sfix.length);
			}

		} else
			result = tmp;

		return result;
	}


	public static boolean isEmptyList(List<? extends Object> list) {
		return list == null || list.isEmpty();
	}

	public static <K, V> boolean isEmptyMap(Map<K, V> map) {
		return map == null || map.isEmpty();
	}

	public static String inputStreamStringfy(java.io.InputStream inputStream){
		StringBuffer sb = new StringBuffer(); 
		try{
			 BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	         String line = null;
	         
	         while ((line = br.readLine()) != null) {
	        	 sb.append(line).append(System.getProperty("line.separator"));
	         }
	         return sb.toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	

	public static<T> T[] arryCast(Object[] objects, Class<T[]> clazz) {
		List<T> re = new ArrayList<T>();
		if(objects!=null){
			for(Object obj:objects){
				re.add((T)obj);
			}
		}
		return (T[]) re.toArray();
	}

	public  static double EARTH_RADIUS = 6378.137*1000;
	public static double rad(double d){
		return d * Math.PI / 180.0;
	}

	/**
	 * unit meters
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2){
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
		Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static double getDistanceSimple(double lat1, double lng1, double lat2, double lng2){
		return Math.sqrt(Math.pow(lat2-lat1,2)+Math.pow(lng2-lng1,2))*111*1000;
	}
	
	
	public static String listStringfy(List<? extends Object> list){
		StringBuffer sb = new StringBuffer();
		if(!isEmptyList(list)){
			for(Object obj:list)
				sb.append("[").append(obj.toString()).append("]");
			return sb.toString();
		}else
			return "[]";
	}
	
	public static void main(String arg[]){
		System.out.println(getDistance( 31.9672407, 118.7573434,31.967249, 118.759047 )/1000);
		System.out.println(getDistanceSimple(20.5876916, -88.9700704,32.911882270158, -97.042651651518));
	}
	
	/**
	 * 
	 * @param input
	 * @param length
	 * @param roundingMode ROUND_UP =           0;ROUND_DOWN =         1;ROUND_CEILING =      2;ROUND_FLOOR =        3;
	 * ROUND_HALF_UP =      4;ROUND_HALF_DOWN =    5;ROUND_HALF_EVEN =    6;ROUND_UNNECESSARY =  7;
	 * @return
	 */
	public static Double doubleRound(Double input,int length,int roundingMode){
		if(input!=null)
			return new BigDecimal(input).setScale(length, roundingMode).doubleValue();
		else
			return null;
	}
	public static String getPid() {
		// get name representing the running Java virtual machine.  
		String name = ManagementFactory.getRuntimeMXBean().getName();  
		// get pid  
		return name.split("@")[0];
	}
	
	public static String globalThreadId (){
		return (getMac()+"_"+getPid() + "_" + Thread.currentThread().getId()).trim();
	}
	
	public static String getMac(){
		byte[] mac;
		try {
			mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();//may return null
			StringBuffer macAddr = new StringBuffer();
			if(mac==null||mac.length<1)
				return null;
            for (int i = 0; i < mac.length; i++) { 
            	macAddr.append(new Formatter().format(Locale.getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString());
            } 
            return macAddr.toString();
		} catch (Exception e) {
			return "";
		}
		
	}
	
	
	    public static String generateGlobalId(){
	    	String key = getMac()+""+DateFormatUtils.format(new Date(), "yyyyMMDDHHmmss");
	    	MD5 m = new MD5();
			return m.getMD5ofStr(key);
	    }
	    
	    public static String escape(String target){
	    	if(target.contains("%") || target.contains("_")){
	    		target = target.replace("%", "\\%").replace("_", "\\_");
	    	}
	    	return target;
	    }
}

