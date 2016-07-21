package org.baoting.te.utils;

import java.util.List;

public class JqueryPage<E> {

	private int sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private int iDisplayStart=1;
	private int iDisplayLength=10;
	
	
	
	public JqueryPage() {
		super();
	}
	public JqueryPage(int iDisplayStart, int iDisplayLength) {
		this.iDisplayStart = iDisplayStart;
		this.iDisplayLength = iDisplayLength;
	}
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	private List<E> aaData;
	public int getsEcho() {
		return sEcho;
	}
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<E> getAaData() {
		return aaData;
	}
	public void setAaData(List<E> aaData) {
		this.aaData = aaData;
	}
}
