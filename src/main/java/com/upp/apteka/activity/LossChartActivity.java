package com.upp.apteka.activity;

import java.util.Map;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.component.chart.TimeSeriesChart;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

@Component("lossChartActivity")
@Scope("prototype")
public class LossChartActivity implements Activity {

	@Autowired
	private JFrame jFrame;

	@Autowired
	@Qualifier("lossTimeSeries")
	private TimeSeriesDataSetGenerator timeSeriesDataSetGenerator;

	@Override
	public void showActivity(Map<String, Object> params) {
		jFrame.add(new TimeSeriesChart(timeSeriesDataSetGenerator, "Втрати аптек"));

	}

}