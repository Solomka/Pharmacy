package com.upp.apteka.component.chart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.upp.apteka.component.combobox.searchable.SearchableItem;
import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.ChartMedicineDto;
import com.upp.apteka.dto.SeriesParam;
import com.upp.apteka.service.chart.TimePickerService;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;
import com.upp.apteka.service.chart.impl.TimePickerServiceImpl;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MedicineTimeSeriesChart<T> extends JPanel {

	private static final long serialVersionUID = -6725377472499598727L;

	private TimeSeriesDataSetGenerator<T> dataGenerator;
	private UtilDateModel startModel;
	private UtilDateModel endModel;

	private JComboBox<TimeComboBoxItem> comboBox;
	private JComboBox<SearchableItem> searchMedicine;

	private JPanel medicinePanel;
	private JPanel chartPanel;

	private static final int MIN_MEDICINE_PANEL_HEIGHT = 80;

	private String title;

	private List<ChartTime<?>> chartTimes;

	@Autowired
	private JFrame jFrame;

	private TimePickerService timePickerService;

	public MedicineTimeSeriesChart(TimeSeriesDataSetGenerator<T> dataGenerator, String title, List<TimeEnum> timeParams,
			JComboBox<SearchableItem> searchMedicine) {
		this.dataGenerator = dataGenerator;
		this.title = title;

		timePickerService = new TimePickerServiceImpl();
		this.chartTimes = timePickerService.getChartTimes(timeParams);

		// add Medicine JCombobox
		medicinePanel = new JPanel();
		this.searchMedicine = searchMedicine;
		JLabel medicineLabel = new JLabel("Виберіть ліки:");

		medicinePanel.setLayout(new GridLayout(0, 1));
		medicinePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		medicinePanel.setPreferredSize(new Dimension(0, MIN_MEDICINE_PANEL_HEIGHT));
		medicinePanel.add(medicineLabel);
		medicinePanel.add(searchMedicine);

		chartPanel = new JPanel();

		setLayout(new BorderLayout());

		// add to Main Panel
		add(medicinePanel, BorderLayout.NORTH);
		add(chartPanel, BorderLayout.CENTER);

		endModel = new UtilDateModel();
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel);
		JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel);

		startModel = new UtilDateModel();
		JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel);
		JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel);

		JButton submitButton = new JButton("Побудувати");

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1, 0));

		controlPanel.add(startDatePicker);
		controlPanel.add(endDatePicker);
		controlPanel.add(buildComboBox());
		controlPanel.add(submitButton);

		add(controlPanel, BorderLayout.SOUTH);

		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					JFreeChart chart = buildChart();
					showChart(chart);
				} catch (IllegalArgumentException exception) {
					JOptionPane.showMessageDialog(jFrame, exception.getMessage(), "Помилка",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	private JComboBox<TimeComboBoxItem> buildComboBox() {
		comboBox = new JComboBox<>();

		for (ChartTime<?> chartTime : chartTimes)
			comboBox.addItem(new TimeComboBoxItem(chartTime.getId(), chartTime.getName()));

		return comboBox;
	}

	private JFreeChart buildChart() {

		Date start = startModel.getValue();
		Date end = endModel.getValue();

		if (start == null || end == null || comboBox.getSelectedItem() == null)
			throw new IllegalArgumentException("Потрібно заповнити всі поля");

		if (end.before(start))
			throw new IllegalArgumentException("Некоректні дати!");

		TimeComboBoxItem value = (TimeComboBoxItem) comboBox.getSelectedItem();

		if (!isValid(value, start, end))
			throw new IllegalArgumentException("Вибрано занадто великий проміжок для даного типу графіку!");

		// get selected Medicine id
		SearchableItem item = (SearchableItem) searchMedicine.getSelectedItem();
		if (item == null) {
			throw new IllegalArgumentException("Не вибрано ліки!");
		}else{
			System.out.println("SELECTED");
		}

		ChartMedicineDto medicine = new ChartMedicineDto(item.getId(), item.getValue());

		List<SeriesParam<T>> seriesParams = dataGenerator.addDataToSeriesParams(medicine);
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		for (SeriesParam<T> sParam : seriesParams) {
			System.out.println("Name: " + sParam.getTitle() + " Value: " + sParam.getValue());
			List<ChartData> chartData = generateData(start, end,
					getCalendarType(((TimeComboBoxItem) comboBox.getSelectedItem()).getTimeEnum()), sParam);

			@SuppressWarnings("deprecation")
			TimeSeries series = new TimeSeries(sParam.getTitle(),
					getAppropriateClass(((TimeComboBoxItem) comboBox.getSelectedItem()).getTimeEnum()));

			for (ChartData data : chartData)
				series.add(new TimeSeriesDataItem(getPeriod(value.getTimeEnum(), data.getDate()), data.getCount()));

			dataset.addSeries(series);
		}

		JFreeChart timechart = ChartFactory.createTimeSeriesChart(title, // Title
				"Час", // X-axis Label
				"Кількість", // Y-axis Label
				dataset, // Dataset
				true, // Show legend
				true, // Use tooltips
				false // Generate URLs
		);
		return timechart;
	}

	private Boolean isValid(TimeComboBoxItem value, Date start, Date end) {
		for (ChartTime<?> chartTime : chartTimes)
			if (value.getTimeEnum() == chartTime.getId())
				return chartTime.validRange(start, end);
		return null;
	}

	private RegularTimePeriod getPeriod(TimeEnum id, Date date) {
		for (ChartTime<?> chartTime : chartTimes)
			if (chartTime.getId() == id)
				return chartTime.getPeriod(date);
		return null;
	}

	private Class<?> getAppropriateClass(TimeEnum id) {
		for (ChartTime<?> chartTime : chartTimes)
			if (chartTime.getId() == id)
				return chartTime.getJfreeChartClass();
		return null;
	}

	private int getCalendarType(TimeEnum id) {
		for (ChartTime<?> chartTime : chartTimes)
			if (chartTime.getId() == id)
				return chartTime.getCalendarId();
		return -1;
	}

	private List<ChartData> generateData(Date start, Date end, int type, SeriesParam<T> param) {

		List<ChartData> data = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(type, 1);

		Date tempDate = calendar.getTime();

		while (start.before(end)) {

			data.add(dataGenerator.generateData(start, tempDate, param));
			start = tempDate;

			calendar.add(type, 1);
			tempDate = calendar.getTime();
		}

		return data;
	}

	private void showChart(JFreeChart chart) {
		chartPanel.removeAll();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
		this.chartPanel.add(chartPanel);
		this.chartPanel.revalidate();
		this.chartPanel.repaint();
	}

}
