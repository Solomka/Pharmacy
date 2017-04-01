package com.upp.apteka.service.chart.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.SeriesParam;
import com.upp.apteka.service.PharmacyService;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

@Service("pharmcyIncomeTimeSeries")
@Transactional
public class PharmacyIncomeTimeSeriesDataSetGenerator implements TimeSeriesDataSetGenerator<Long> {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private PharmacyService pharmacyService;

	private String sql = "SELECT sum(pack_quantity * (SELECT pack_price" + " FROM pharmacy_medicine"
			+ " WHERE id_medicine = purch_medicine.id_medicine AND id_pharmacy = :id))"
			+ " FROM purchase INNER JOIN purch_medicine ON purchase.id = purch_medicine.id_purchase"
			+ " WHERE `date` >= :start AND `date` < :end AND id_pharmacy = :id";

	@Override
	public ChartData generateData(Date startDate, Date finishDate, SeriesParam<Long> timeSeriesParam) {
		BigDecimal bdValue = (BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setDate("start", startDate).setDate("end", finishDate).setLong("id", timeSeriesParam.getValue()).list()
				.get(0);
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
	public List<SeriesParam<Long>> getTimeSeriesParam() {
		List<Pharmacy> pharmacies = pharmacyService.getAll();

		List<SeriesParam<Long>> params = new ArrayList<>();

		for (Pharmacy pharmacy : pharmacies) {
			params.add(new SeriesParam<Long>(pharmacy.getName(), pharmacy.getId()));
		}
		return params;
	}

}
