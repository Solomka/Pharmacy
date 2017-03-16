package com.upp.apteka.dto;

public class SeriesParam<T> {
	private String title;
	private T value;

	public SeriesParam(String title, T value) {
		super();
		this.title = title;
		this.value = value;
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

	@Override
	public String toString() {
		return "SeriesParam [title=" + title + ", value=" + value + "]";
	}

}
