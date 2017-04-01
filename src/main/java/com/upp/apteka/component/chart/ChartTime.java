package com.upp.apteka.component.chart;

import java.util.Date;

import org.jfree.data.time.RegularTimePeriod;

public abstract class ChartTime<T> {
	protected TimeEnum id;
	
	protected int calendarId;
	protected Class<T> jfreeChartClass;
	protected String name;
	
	public abstract RegularTimePeriod getPeriod( Date date);

	public TimeEnum getId() {
		return id;
	}
	
	public abstract boolean validRange(Date start, Date finish);

	public void setId(TimeEnum id) {
		this.id = id;
	}

	public int getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public Class<T> getJfreeChartClass() {
		return jfreeChartClass;
	}

	public void setJfreeChartClass(Class<T> jfreeChartClass) {
		this.jfreeChartClass = jfreeChartClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
