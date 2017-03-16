package com.upp.apteka.component.chart;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.upp.apteka.dto.ChartData;
import com.upp.apteka.dto.SeriesParam;
import com.upp.apteka.service.chart.TimeSeriesDataSetGenerator;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class TimeSeriesChart<T> extends JPanel {

	private static final long serialVersionUID = -6725377472499598727L;

	private TimeSeriesDataSetGenerator<T> dataGenerator;
	private UtilDateModel startModel;
	private UtilDateModel endModel;

	private JComboBox<String> comboBox;

	private JPanel chartPanel;
	private String title;

	@Autowired
	private JFrame jFrame;

	public TimeSeriesChart(TimeSeriesDataSetGenerator<T> dataGenerator, String title) {
		this.dataGenerator = dataGenerator;
		this.title = title;

		chartPanel = new JPanel();

		setLayout(new BorderLayout());

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
					JOptionPane.showMessageDialog(jFrame, "Помилка", exception.getMessage(),
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	private JComboBox<String> buildComboBox() {
		comboBox = new JComboBox<>();
		comboBox.addItem("День");
		comboBox.addItem("Місяць");

		return comboBox;
	}

	private JFreeChart buildChart() {

		Date start = startModel.getValue();
		Date end = endModel.getValue();

		if (start == null || end == null || comboBox.getSelectedItem() == null)
			throw new IllegalArgumentException("Потрібно заповнити всі поля");

		if (end.before(start))
			throw new IllegalArgumentException("Некоректні дати!");

		String value = (String) comboBox.getSelectedItem();

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		for (SeriesParam<T> sParam : dataGenerator.getTimeSeriesParam()) {
			List<ChartData> chartData = generateData(start, end, getCalendarType((String) comboBox.getSelectedItem()),
					sParam);
			
			@SuppressWarnings("deprecation")
			TimeSeries series = new TimeSeries(sParam.getTitle(),
					getAppropriateClass((String) comboBox.getSelectedItem()));

			for (ChartData data : chartData)
				series.add(new TimeSeriesDataItem(getPeriod(value, data.getDate()), data.getCount()));

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

	private RegularTimePeriod getPeriod(String value, Date date) {
		if (value.equals("Година"))
			return getHour(date);
		else if (value.equals("День"))
			return getDay(date);
		else if (value.equals("Місяць"))
			return getMonth(date);
		return null;
	}

	private RegularTimePeriod getDay(Date date) {
		return new Day(date);
	}

	private RegularTimePeriod getMonth(Date date) {
		return new Month(date);
	}

	private RegularTimePeriod getHour(Date date) {
		return new Hour(date);
	}

	private Class<?> getAppropriateClass(String value) {
		if (value.equals("Година"))
			return Hour.class;
		else if (value.equals("День"))
			return Day.class;
		else if (value.equals("Місяць"))
			return Month.class;
		return null;
	}

	private int getCalendarType(String value) {
		if (value.equals("Година"))
			return Calendar.HOUR;
		else if (value.equals("День"))
			return Calendar.DAY_OF_MONTH;
		else if (value.equals("Місяць"))
			return Calendar.MONTH;
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
