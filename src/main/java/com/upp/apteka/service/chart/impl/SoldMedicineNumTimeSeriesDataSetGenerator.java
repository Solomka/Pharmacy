package com.upp.apteka.service.chart.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.ChartMedicineDto;
import com.upp.apteka.dto.SeriesParam;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

@Service("soldMedicineNumTimeSeries")
@Transactional
public class SoldMedicineNumTimeSeriesDataSetGenerator implements TimeSeriesDataSetGenerator<Long> {

	@Autowired
	private SessionFactory sessionFactory;
	
	private List<SeriesParam<Long>> params;

	private String sql = "SELECT sum(pack_quantity)"
			+ " FROM purchase INNER JOIN purch_medicine ON purchase.id = purch_medicine.id_purchase"
			+ " WHERE `date` >= :start AND `date` < :end AND id_medicine = :id";

	@Override
	public ChartData generateData(Date startDate, Date finishDate, SeriesParam<Long> timeSeriesParam) {

		BigDecimal bdValue = ((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setDate("start", startDate).setDate("end", finishDate).setLong("id", timeSeriesParam.getValue()).list()
				.get(0));

		Double value = 0D;

		if (bdValue != null)
			value = bdValue.doubleValue();

		ChartData chartData = new ChartData();
		chartData.setCount(value);
		chartData.setDate(startDate);
		return chartData;

	}

	@Override
	public List<SeriesParam<Long>> getTimeSeriesParam() {
		List<SeriesParam<Long>> params = new ArrayList<SeriesParam<Long>>();
		return params;
	}
	
	@Override
	public List<SeriesParam<Long>> addDataToSeriesParams(ChartMedicineDto medicine){
		List<SeriesParam<Long>> params = getTimeSeriesParam();		
		params.add(new SeriesParam<Long>(medicine.getName(), medicine.getId()));	
		
		return params;
	}

}
