package com.upp.apteka.component.chart.time;

import java.util.Calendar;
import java.util.Date;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Week;

import com.upp.apteka.component.chart.ChartTime;
import com.upp.apteka.component.chart.TimeEnum;

public class WeekChartTime extends ChartTime<Week>{
	
	public WeekChartTime(){
		super.calendarId = Calendar.WEEK_OF_MONTH;
		super.id = TimeEnum.WEEK;
		super.jfreeChartClass = Week.class;
		super.name = "Тиждень";
	}

	@Override
	public RegularTimePeriod getPeriod(Date date) {
		return new Week(date);
	}
	
	@Override
	public boolean validRange(Date start, Date finish) {
		long startMilli = start.toInstant().toEpochMilli();
		long endMilli = finish.toInstant().toEpochMilli();
		
		long diff = (endMilli - startMilli) / 1000 / 60 / 60 / 24;

		return (diff < 500);
	}


}
