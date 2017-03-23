package com.upp.apteka.service.chart;

import java.util.List;

import com.upp.apteka.component.chart.ChartTime;
import com.upp.apteka.component.chart.TimeEnum;

public interface TimePickerService {
	List<ChartTime<?>> getChartTimes(List<TimeEnum> timeParams);
}
