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
import com.upp.apteka.dto.SeriesParam;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

@Service("lossTimeSeries")
@Transactional
public class LossTimeSeriesDataSetGenerator implements TimeSeriesDataSetGenerator<String> {

	@Autowired
	private SessionFactory sessionFactory;

	private String sql = "SELECT sum(box_quantity* (SELECT box_price FROM medicine WHERE id = id_medicine)) FROM delivery_medicine WHERE id_delivery IN (SELECT id FROM delivery WHERE `date` >= :start AND `date` < :end)";

	@Override
	public ChartData generateData(Date startDate, Date finishDate, SeriesParam<String> object) {
		BigDecimal bdValue = ((BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setDate("start", startDate).setDate("end", finishDate).list().get(0));

		Double value = 0D;

		if (bdValue != null)
			value = bdValue.doubleValue();

		ChartData chartData = new ChartData();
		chartData.setCount(value);
		chartData.setDate(startDate);
		return chartData;
	}

	@Override
	public List<SeriesParam<String>> getTimeSeriesParam() {
		List<SeriesParam<String>> params = new ArrayList<>();
		params.add(new SeriesParam<String>("Втрати", "Щось"));
		return params;
	}

}
