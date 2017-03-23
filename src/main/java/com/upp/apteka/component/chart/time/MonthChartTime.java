package com.upp.apteka.component.chart.time;

import java.util.Calendar;
import java.util.Date;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Month;

import com.upp.apteka.component.chart.ChartTime;
import com.upp.apteka.component.chart.TimeEnum;

public class MonthChartTime extends ChartTime<Month> {

	public MonthChartTime() {
		super.calendarId = Calendar.MONTH;
		super.id = TimeEnum.MONTH;
		super.jfreeChartClass = Month.class;
		super.name = "Місяць";
	}

	@Override
	public RegularTimePeriod getPeriod(Date date) {
		return new Month(date);
	}

	@Override
	public boolean validRange(Date start, Date finish) {
		long startMilli = start.toInstant().toEpochMilli();
		long endMilli = finish.toInstant().toEpochMilli();
		
		long diff = (endMilli - startMilli) / 1000 / 60 / 60 / 24;

		return (diff < 1000);
	}

}
