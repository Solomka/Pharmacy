package com.upp.apteka.service.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.ChartMedicineDto;
import com.upp.apteka.dto.SeriesParam;

public interface TimeSeriesDataSetGenerator<T> {
	ChartData generateData(Date startDate, Date finishDate, SeriesParam<T> timeSeriesParam);

	List<SeriesParam<T>> getTimeSeriesParam();

	default List<SeriesParam<T>> addDataToSeriesParams(ChartMedicineDto medicine) {

		List<SeriesParam<T>> res = new ArrayList<>();
		return res;
	}

}
