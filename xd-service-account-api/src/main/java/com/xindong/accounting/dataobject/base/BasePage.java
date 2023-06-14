package com.xindong.accounting.dataobject.base;

import java.io.Serializable;
import java.util.List;

public class BasePage <E extends Serializable> implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<E> rows;
	private long total;
	private String positionStr;

	private BasePage() {
	}

	private BasePage(List<E> rows, long total) {
		this.rows = rows;
		this.total = total;
	}

	private BasePage(List<E> rows, long total, String positionStr) {
		this(rows, total);
		this.positionStr = positionStr;
	}

	private BasePage(List<E> rows) {
		this.rows = rows;
		this.total = (long)rows.size();
	}

	private BasePage(List<E> rows, String positionStr) {
		this(rows);
		this.positionStr = positionStr;
	}

	public static <T extends Serializable> BasePage<T> build(List<T> rows) {
		return new BasePage(rows);
	}

	public static <T extends Serializable> BasePage<T> build(List<T> rows, String positionStr) {
		return new BasePage(rows, positionStr);
	}

	public static <T extends Serializable> BasePage<T> build(List<T> rows, long total, String positionStr) {
		return new BasePage(rows, total, positionStr);
	}

	public static <T extends Serializable> BasePage<T> build(List<T> rows, long total) {
		return new BasePage(rows, total);
	}

	public List<E> getRows() {
		return this.rows;
	}

	public long getTotal() {
		return this.total;
	}

	public String getPositionStr() {
		return this.positionStr;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}

	protected boolean canEqual(Object other) {
		return other instanceof BasePage;
	}


	public String toString() {
		return "BasePage(rows=" + this.getRows() + ", total=" + this.getTotal() + ", positionStr=" + this.getPositionStr() + ")";
	}
}
