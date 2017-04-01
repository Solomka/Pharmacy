package com.upp.apteka.service.chart;

import java.util.Date;
import java.util.List;

import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.SeriesParam;

public interface TimeSeriesDataSetGenerator<T> {
	ChartData generateData(Date startDate, Date finishDate, SeriesParam<T> timeSeriesParam);
	
	List<SeriesParam<T>> getTimeSeriesParam();
}
