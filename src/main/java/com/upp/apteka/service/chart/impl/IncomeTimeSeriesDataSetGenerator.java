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

@Service("incomeTimeSeries")
@Transactional
public class IncomeTimeSeriesDataSetGenerator implements TimeSeriesDataSetGenerator<String> {

	@Autowired
	private SessionFactory sessionFactory;

	private String sql = "SELECT sum(pack_quantity * (SELECT pack_price"
			+ 														" FROM pharmacy_medicine"
			+ 														" WHERE id_medicine = purch_medicine.id_medicine AND id_pharmacy = purchase.id_pharmacy))"
			+ 			" FROM purchase INNER JOIN purch_medicine ON purchase.id = purch_medicine.id_purchase" 
			+ 			" WHERE `date` >= :start AND `date` < :end";

	@Override
	public ChartData generateData(Date startDate, Date finishDate, SeriesParam<String> object) {
		BigDecimal bdValue = (BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setDate("start", startDate).setDate("end", finishDate).list().get(0);

		Double value = 0D;

		if (bdValue != null) {
			value = bdValue.doubleValue();
		}

		ChartData chartData = new ChartData();
		chartData.setCount(value);
		chartData.setDate(startDate);
		return chartData;
	}

	@Override
	public List<SeriesParam<String>> getTimeSeriesParam() {
		List<SeriesParam<String>> params = new ArrayList<>();
		params.add(new SeriesParam<String>("Доходи", "Щось"));
		return params;
	}

}
