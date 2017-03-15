package com.upp.apteka.service.chart;

import java.util.Date;

import com.upp.apteka.dto.ChartData;

public interface TimeSeriesDataSetGenerator {
	ChartData generateData(Date startDate, Date finishDate);
}
