package com.upp.apteka.service.chart.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.SeriesParam;
import com.upp.apteka.service.PharmacyService;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

@Component("pharmacyProfitTimeSeries")
public class PharmacyProfitTimeSeriesDataSetGenerator implements TimeSeriesDataSetGenerator<Long> {

	@Autowired
	@Qualifier("pharmcyIncomeTimeSeries")
	TimeSeriesDataSetGenerator<Long> pharmcyIncomeTimeSeries;

	@Autowired
	@Qualifier("pharmacyLossTimeSeries")
	TimeSeriesDataSetGenerator<Long> pharmacyLossTimeSeries;

	@Autowired
	PharmacyService pharmacyService;

	@Override
	public ChartData generateData(Date startDate, Date finishDate, SeriesParam<Long> timeSeriesParam) {

		Double pharmacyProfit = pharmcyIncomeTimeSeries.generateData(startDate, finishDate, timeSeriesParam).getCount()
				- pharmacyLossTimeSeries.generateData(startDate, finishDate, timeSeriesParam).getCount();

		ChartData chartData = new ChartData();
		chartData.setCount(pharmacyProfit);
		chartData.setDate(startDate);

		return chartData;
	}

	@Override
	public List<SeriesParam<Long>> getTimeSeriesParam() {
		List<Pharmacy> pharmacies = pharmacyService.getAll();

		List<SeriesParam<Long>> params = new ArrayList<>();

		for (Pharmacy pharmacy : pharmacies) {
			params.add(new SeriesParam<Long>(pharmacy.getName(), pharmacy.getId()));
		}
		return params;
	}

}
