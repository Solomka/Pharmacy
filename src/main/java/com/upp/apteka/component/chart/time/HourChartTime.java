package com.upp.apteka.component.chart.time;

import java.util.Calendar;
import java.util.Date;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Hour;

import com.upp.apteka.component.chart.ChartTime;
import com.upp.apteka.component.chart.TimeEnum;

public class HourChartTime extends ChartTime<Hour>{
	
	public HourChartTime(){
		super.calendarId = Calendar.HOUR;
		super.id = TimeEnum.HOUR;
		super.jfreeChartClass = Hour.class;
		super.name = "Година";
	}

	@Override
	public RegularTimePeriod getPeriod(Date date) {
		return new Hour(date);
	}

	@Override
	public boolean validRange(Date start, Date finish) {
		long startMilli = start.toInstant().toEpochMilli();
		long endMilli = finish.toInstant().toEpochMilli();
		
		long diff = (endMilli - startMilli) / 1000 / 60 / 60 / 24;

		return (diff < 10);
	}

}
