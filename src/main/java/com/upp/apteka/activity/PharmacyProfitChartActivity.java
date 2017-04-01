package com.upp.apteka.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.component.chart.TimeEnum;
import com.upp.apteka.component.chart.TimeSeriesChart;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

@Component("pharmacyProfitChartActivity")
@Scope("prototype")
public class PharmacyProfitChartActivity implements Activity {

	@Autowired
	private JFrame jFrame;

	@Autowired
	@Qualifier("pharmacyProfitTimeSeries")
	private TimeSeriesDataSetGenerator<Long> timeSeriesDataSetGenerator;

	@Override
	public void showActivity(Map<String, Object> params) {

		List<TimeEnum> timeEnums = new ArrayList<TimeEnum>();
		timeEnums.add(TimeEnum.DAY);
		timeEnums.add(TimeEnum.WEEK);
		timeEnums.add(TimeEnum.MONTH);

		jFrame.add(new TimeSeriesChart<Long>(timeSeriesDataSetGenerator, "Прибутки кожної аптеки мережі", timeEnums));

	}

}
