package com.upp.apteka.component.chart;

public class TimeComboBoxItem {
	private TimeEnum timeEnum;
	private String value;

	public TimeComboBoxItem(TimeEnum timeEnum, String value) {
		super();
		this.timeEnum = timeEnum;
		this.value = value;
	}

	public TimeEnum getTimeEnum() {
		return timeEnum;
	}

	public void setTimeEnum(TimeEnum timeEnum) {
		this.timeEnum = timeEnum;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
