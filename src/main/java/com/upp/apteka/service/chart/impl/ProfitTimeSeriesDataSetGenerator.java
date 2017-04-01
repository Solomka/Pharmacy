package com.upp.apteka.service.chart.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.SeriesParam;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

@Service("profitTimeSeries")
public class ProfitTimeSeriesDataSetGenerator implements TimeSeriesDataSetGenerator<String> {

	@Autowired
	@Qualifier("incomeTimeSeries")
	private TimeSeriesDataSetGenerator<String> incomeTimeSeries;

	@Autowired
	@Qualifier("lossTimeSeries")
	private TimeSeriesDataSetGenerator<String> lossTimeSeries;

	@Override
	public ChartData generateData(Date startDate, Date finishDate, SeriesParam<String> timeSeriesParam) {

		Double generalProfit = incomeTimeSeries.generateData(startDate, finishDate, timeSeriesParam).getCount()
				- lossTimeSeries.generateData(startDate, finishDate, timeSeriesParam).getCount();

		ChartData chartData = new ChartData();
		chartData.setCount(generalProfit);
		chartData.setDate(startDate);

		return chartData;
	}

	@Override
	public List<SeriesParam<String>> getTimeSeriesParam() {
		List<SeriesParam<String>> params = new ArrayList<>();
		params.add(new SeriesParam<String>("Прибутки", ""));
		return params;
	}

}
