package com.upp.apteka.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.component.chart.MedicineTimeSeriesChart;
import com.upp.apteka.component.chart.TimeEnum;
import com.upp.apteka.component.combobox.searchable.SearchableComboBox;
import com.upp.apteka.component.combobox.searchable.SearchableItem;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;
import com.upp.apteka.service.searchable.SearchableMedicineService;

@Component("soldMedicineNumChartActivity")
@Scope("prototype")
public class SoldMedicineNumChartActivity implements Activity {

	@Autowired
	JFrame jFrame;

	@Autowired
	@Qualifier("soldMedicineNumTimeSeries")
	private TimeSeriesDataSetGenerator<Long> timeSeriesDataSetGenerator;

	@Autowired
	private SearchableMedicineService searchableMedicineService;

	private JComboBox<SearchableItem> searchMedicine;

	@Override
	public void showActivity(Map<String, Object> params) {

		List<TimeEnum> timeEnums = new ArrayList<TimeEnum>();
		timeEnums.add(TimeEnum.DAY);
		timeEnums.add(TimeEnum.WEEK);
		timeEnums.add(TimeEnum.MONTH);

		searchMedicine = new SearchableComboBox(searchableMedicineService);

		jFrame.add(new MedicineTimeSeriesChart<Long>(timeSeriesDataSetGenerator, "К-сть проданих одиниць ліків у мережі аптек",
				timeEnums, searchMedicine));

	}

}
