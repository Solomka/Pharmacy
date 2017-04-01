package com.upp.apteka.component.chart.time;

import java.util.Calendar;
import java.util.Date;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Day;

import com.upp.apteka.component.chart.ChartTime;
import com.upp.apteka.component.chart.TimeEnum;

public class DayChartTime extends ChartTime<Day> {

	public DayChartTime() {
		super.calendarId = Calendar.DAY_OF_MONTH;
		super.id = TimeEnum.DAY;
		super.jfreeChartClass = Day.class;
		super.name = "День";
	}

	@Override
	public RegularTimePeriod getPeriod(Date date) {
		return new Day(date);
	}

	@Override
	public boolean validRange(Date start, Date finish) {
		long startMilli = start.toInstant().toEpochMilli();
		long endMilli = finish.toInstant().toEpochMilli();
		
		long diff = (endMilli - startMilli) / 1000 / 60 / 60 / 24;

		return (diff < 120);
	}

}
