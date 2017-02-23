package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.Prescription;
import com.upp.apteka.config.Mapper;
import com.upp.apteka.service.PrescriptionService;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

@Component("allPrescriptionsActivity")
@Scope("prototype")
public class AllPrescriptions implements Activity {

	@Autowired
	private Mapper mapper;

	@Autowired
	private JFrame jFrame;

	private JTable prescriptionsTable;

	private Object[] columnsHeader = new String[] { "Номер", "Дата", "Пацієнт", "Ліки", "Лікар", "Статус" };

	private JTextField queryField;

	private static final int BUTTON_HEIGHT = 20;
	private static final int BUTTON_WIDTH = 120;

	private static final int PAGINATION_BUTTON_HEIGHT = 25;
	private static final int PAGINATION_BUTTON_WIDTH = 90;

	private static final int ROW_HEIGHT = 32;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	private List<Prescription> prescriptions;

	private int lastPage;
	private int currentPage;

	private Date startDate;
	private Date endDate;

	private Boolean sold;

	private String query;

	private UtilDateModel endModel;
	private UtilDateModel startModel;

	private JRadioButton soldButton;
	private JRadioButton availableButton;
	private JRadioButton none;

	@Autowired
	private PrescriptionService prescriptionService;

	@SuppressWarnings("unchecked")
	//@Override
	public void showActivity(final Map<String, Object> params) {

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		jFrame.setContentPane(mainPanel);

		prescriptions = (List<Prescription>) params.get("prescriptions");
		jFrame.setLayout(new BorderLayout());

		lastPage = (Integer) params.get("last");
		currentPage = (Integer) params.get("current");
		startDate = (Date) params.get("startDate");
		endDate = (Date) params.get("endDate");
		sold = (Boolean) params.get("sold");

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel fieldWrapper = new JPanel();
		queryField = new JTextField();

		JButton queryButton = new JButton("Шукати");
		queryButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		queryField.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT - 5));

		startModel = new UtilDateModel();
		endModel = new UtilDateModel();

		soldButton = new JRadioButton("Sold");
		availableButton = new JRadioButton("Active");
		none = new JRadioButton("None");

		final ButtonGroup bg = new ButtonGroup();

		if (sold == null)
			none.setSelected(true);
		else if (sold)
			soldButton.setSelected(true);
		else
			availableButton.setSelected(true);

		bg.add(soldButton);
		bg.add(availableButton);
		bg.add(none);

		JPanel groupPanel = new JPanel();
		groupPanel.add(soldButton);
		groupPanel.add(availableButton);
		groupPanel.add(none);

		queryButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				params.put("query", queryField.getText());
				params.put("startDate", startModel.getValue());
				params.put("endDate", endModel.getValue());

				if (soldButton.isSelected())
					params.put("sold", true);
				else if (availableButton.isSelected())
					params.put("sold", false);
				else
					params.put("sold", null);

				mapper.changeActivity("allPrescriptions", params);
			}
		});

		query = (String) params.get("query");
		queryField.setText(query);

		fieldWrapper.setLayout(new GridLayout(0, 1));
		fieldWrapper.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		fieldWrapper.add(queryField);

		JPanel contPanel = new JPanel();
		contPanel.setLayout(new FlowLayout());

		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(30, BUTTON_HEIGHT));
		searchPanel.add(emptyPanel, BorderLayout.WEST);

		JLabel infoLabel = new JLabel("Сторінка: " + currentPage + "/" + lastPage);
		infoLabel.setFont(font);

		contPanel.add(infoLabel);
		contPanel.add(emptyPanel);

		searchPanel.add(contPanel, BorderLayout.WEST);
		searchPanel.add(fieldWrapper, BorderLayout.CENTER);
		searchPanel.add(queryButton, BorderLayout.EAST);

		if (startDate != null && endDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int year = cal.get(Calendar.YEAR);
			endModel.setDate(year, month, day);
			endModel.setSelected(true);

			cal.setTime(startDate);
			month = cal.get(Calendar.MONTH);
			day = cal.get(Calendar.DAY_OF_MONTH);
			year = cal.get(Calendar.YEAR);
			startModel.setDate(year, month, day);
			startModel.setSelected(true);
		}

		JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel);
		JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel);

		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel);
		JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel);

		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout());

		JLabel startLabel = new JLabel("Початок: ");
		startLabel.setFont(font);
		datePanel.add(startLabel);
		datePanel.add(startDatePicker);

		JLabel endLabel = new JLabel("Кінець: ");
		endLabel.setFont(font);
		datePanel.add(endLabel);
		datePanel.add(endDatePicker);
		datePanel.add(groupPanel);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(datePanel, BorderLayout.NORTH);
		topPanel.add(searchPanel, BorderLayout.SOUTH);

		jFrame.add(topPanel, BorderLayout.NORTH);

		prescriptionsTable = new JTable(getData(prescriptions), columnsHeader);
		prescriptionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		prescriptionsTable.setFont(font);
		prescriptionsTable.setRowHeight(ROW_HEIGHT);
		prescriptionsTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		prescriptionsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY));

		prescriptionsTable.getColumnModel().getColumn(0).setMaxWidth(50);
		prescriptionsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		prescriptionsTable.getColumnModel().getColumn(1).setMaxWidth(85);
		prescriptionsTable.getColumnModel().getColumn(1).setPreferredWidth(85);
		prescriptionsTable.getColumnModel().getColumn(3).setMaxWidth(50);
		prescriptionsTable.getColumnModel().getColumn(3).setPreferredWidth(50);

		JScrollPane scrollPane = new JScrollPane(prescriptionsTable);
		scrollPane.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
		jFrame.add(scrollPane, BorderLayout.CENTER);

		JPanel paginationPanel = new JPanel();
		paginationPanel.setLayout(new BorderLayout());

		JButton nextButton = new JButton("Далі");
		nextButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		nextButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				params.put("current", currentPage + 1);
				mapper.changeActivity("allPrescriptions", params);
			}
		});

		if (currentPage == lastPage)
			nextButton.setEnabled(false);

		JButton prevButton = new JButton("Назад");
		prevButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		prevButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				params.put("current", currentPage - 1);
				mapper.changeActivity("allPrescriptions", params);
			}
		});

		if (currentPage == 1)
			prevButton.setEnabled(false);

		JPanel goPanel = new JPanel();
		final JTextField goTo = new JTextField(10);
		goPanel.setLayout(new GridLayout(0, 1));
		goPanel.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));
		goPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		goPanel.add(goTo);

		JButton editButton = new JButton("Змінити");
		editButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		editButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = prescriptionsTable.getSelectedRow();

				if (selectedRow != -1) {
					Long id = prescriptions.get(selectedRow).getId();

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);

					mapper.changeActivity("addPrescription", params);
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Виберіть спочатку покупку!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton viewButton = new JButton("Деталі");
		viewButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));
		viewButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = prescriptionsTable.getSelectedRow();

				if (selectedRow != -1) {
					Long id = prescriptions.get(selectedRow).getId();

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);

					mapper.changeActivity("viewPrescription", params);
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Виберіть спочатку покупку!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton removeButton = new JButton("Видалити");
		removeButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		removeButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = prescriptionsTable.getSelectedRow();

				if (selectedRow != -1) {
					Long id = prescriptions.get(selectedRow).getId();

					boolean success = prescriptionService.delete(id);

					if (success){
						JOptionPane.showMessageDialog(jFrame, "Рецент успішно видалено!", "Успішна операція",
								JOptionPane.INFORMATION_MESSAGE);
						mapper.changeActivity("allPrescriptions", params);
					}
					else {
						JOptionPane.showMessageDialog(jFrame, new String[] { "Є покупки, альо. Спробуйте пізніше." },
								"Помилка", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Виберіть спочатку покупку!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton goButton = new JButton("Перейти");
		goButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));
		goButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int page = Integer.valueOf(goTo.getText());

					if (page > lastPage)
						page = lastPage;

					if (page < 0)
						page = 0;

					params.put("current", page);
					mapper.changeActivity("allPrescriptions", params);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Числа нормальні треба вводити!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		paginationPanel.setLayout(new FlowLayout());
		paginationPanel.add(nextButton, BorderLayout.WEST);
		paginationPanel.add(goPanel, BorderLayout.WEST);
		paginationPanel.add(goButton, BorderLayout.WEST);
		paginationPanel.add(prevButton, BorderLayout.WEST);
		paginationPanel.add(editButton, BorderLayout.WEST);
		paginationPanel.add(removeButton, BorderLayout.WEST);
		paginationPanel.add(viewButton, BorderLayout.WEST);

		jFrame.add(paginationPanel, BorderLayout.SOUTH);
	}

	private Object[][] getData(List<Prescription> prescriptions) {
		Object[][] array = new Object[prescriptions.size()][columnsHeader.length];

		for (int i = 0; i < prescriptions.size(); i++) {
			array[i][0] = prescriptions.get(i).getId();
			array[i][1] = prescriptions.get(i).getDate();
			array[i][2] = prescriptions.get(i).getPatient().getSurname() + " "
					+ prescriptions.get(i).getPatient().getName();
			array[i][3] = prescriptions.get(i).getPrescriptionMedicines().size();
			array[i][4] = prescriptions.get(i).getDoctor().getSurname() + " "
					+ prescriptions.get(i).getDoctor().getName();

			if (prescriptions.get(i).getSold())
				array[i][5] = "Продано";
			else
				array[i][5] = "Доступно";
		}

		return array;
	}
}
