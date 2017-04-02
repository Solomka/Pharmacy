package com.upp.apteka.dto;

public class SeriesParam<T> {
	private String title;
	private T value;
	// for instance medicineId
	private T subValue;

	public SeriesParam(String title, T value) {
		super();
		this.title = title;
		this.value = value;
	}

	public SeriesParam(String title, T value, T subValue) {
		super();
		this.title = title;
		this.value = value;
		this.subValue = subValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getSubValue() {
		return subValue;
	}

	public void setSubValue(T subValue) {
		this.subValue = subValue;
	}

	@Override
	public String toString() {
		return "SeriesParam [title=" + title + ", value=" + value + ", subValue=" + subValue + "]";
	}

}
