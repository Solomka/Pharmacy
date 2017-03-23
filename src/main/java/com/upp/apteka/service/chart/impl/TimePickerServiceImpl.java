package com.upp.apteka.service.chart.impl;

import java.util.ArrayList;
import java.util.List;

import com.upp.apteka.component.chart.ChartTime;
import com.upp.apteka.component.chart.TimeEnum;
import com.upp.apteka.component.chart.time.DayChartTime;
import com.upp.apteka.component.chart.time.HourChartTime;
import com.upp.apteka.component.chart.time.MonthChartTime;
import com.upp.apteka.component.chart.time.WeekChartTime;
import com.upp.apteka.service.chart.*;

public class TimePickerServiceImpl implements TimePickerService{

	public List<ChartTime<?>> getChartTimes(List<TimeEnum> timeParams) {
		List<ChartTime<?>> chartTimes = new ArrayList<>();

		for (TimeEnum timeEnum : timeParams)
			if (timeEnum == TimeEnum.DAY)
				chartTimes.add(new DayChartTime());
			else if (timeEnum == TimeEnum.MONTH)
				chartTimes.add(new MonthChartTime());
			else if (timeEnum == TimeEnum.HOUR)
				chartTimes.add(new HourChartTime());
			else if (timeEnum == TimeEnum.WEEK)
				chartTimes.add(new WeekChartTime());

		return chartTimes;
	}
}
