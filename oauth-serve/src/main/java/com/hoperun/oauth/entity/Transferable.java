package com.hoperun.oauth.entity;

/**
 * @ClassName IDao.java
 * @Description any class implements this interface means can transfer to vo or po with method transfer
 * @author yin_changbao
 * @Date May 23, 2016 1:40:13 PM
 *
 */
@SuppressWarnings("rawtypes")
public interface Transferable<E extends Transferable> extends java.io.Serializable{
	/**
	 * transfer PO to VO or VO to PO
	 * @return
	 */
	E transfer();

}
