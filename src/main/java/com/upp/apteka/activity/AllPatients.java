package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.config.Mapper;
import com.upp.apteka.service.PatientService;

@Component("allPatientsActivity")
@Scope("prototype")
public class AllPatients implements Activity {

	@Autowired
	private Mapper mapper;

	@Autowired
	private JFrame jFrame;

	private JTable patientsTable;

	private Object[] columnsHeader = new String[] { "Прізвище", "Ім'я", "Номер телефону" };

	private JTextField queryField;

	private static final int BUTTON_HEIGHT = 20;
	private static final int BUTTON_WIDTH = 120;

	private static final int PAGINATION_BUTTON_HEIGHT = 25;
	private static final int PAGINATION_BUTTON_WIDTH = 90;

	private static final int ROW_HEIGHT = 32;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	private List<Patient> patients;

	private int lastPage;
	private int currentPage;

	private String query;

	@Autowired
	private PatientService patientService;

	@SuppressWarnings("unchecked")
	@Override
	public void showActivity(final Map<String, Object> params) {

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		jFrame.setContentPane(mainPanel);

		patients = (List<Patient>) params.get("patients");
		jFrame.setLayout(new BorderLayout());

		lastPage = (int) params.get("last");
		currentPage = (int) params.get("current");

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel fieldWrapper = new JPanel();
		queryField = new JTextField();

		JButton queryButton = new JButton("Шукати");
		queryButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		queryField.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT - 5));

		queryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, Object> params = new HashMap<>();

				params.put("current", currentPage);
				params.put("query", queryField.getText());

				mapper.changeActivity("allPatients", params);
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

		jFrame.add(searchPanel, BorderLayout.NORTH);

		patientsTable = new JTable(getData(patients), columnsHeader);
		patientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		patientsTable.setFont(font);
		patientsTable.setRowHeight(ROW_HEIGHT);
		patientsTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		patientsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JScrollPane scrollPane = new JScrollPane(patientsTable);
		scrollPane.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
		jFrame.add(scrollPane, BorderLayout.CENTER);

		JPanel paginationPanel = new JPanel();
		paginationPanel.setLayout(new BorderLayout());

		JButton nextButton = new JButton("Далі");
		nextButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, Object> params = new HashMap<>();

				params.put("current", currentPage + 1);
				params.put("query", query);

				mapper.changeActivity("allPatients", params);
			}
		});

		if (currentPage == lastPage)
			nextButton.setEnabled(false);

		JButton prevButton = new JButton("Назад");
		prevButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		prevButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, Object> params = new HashMap<>();

				params.put("current", currentPage - 1);
				params.put("query", query);

				mapper.changeActivity("allPatients", params);
			}
		});

		if (currentPage == 1)
			prevButton.setEnabled(false);

		JPanel goPanel = new JPanel();
		JTextField goTo = new JTextField(10);
		goPanel.setLayout(new GridLayout(0, 1));
		goPanel.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));
		goPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		goPanel.add(goTo);

		JButton editButton = new JButton("Змінити");
		editButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = patientsTable.getSelectedRow();

				if (selectedRow != -1) {
					Long id = patients.get(selectedRow).getId();

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);

					mapper.changeActivity("addPatient", params);
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Виберіть спочатку пацієнта!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton removeButton = new JButton("Видалити");
		removeButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = patientsTable.getSelectedRow();

				if (selectedRow != -1) {
					Long id = patients.get(selectedRow).getId();

					boolean success = patientService.delete(id);

					if (success)
						mapper.changeActivity("allPatients", params);
					else {
						JOptionPane.showMessageDialog(jFrame, new String[] { "Пацієнт купував багато. Так що ні-ні." },
								"Помилка", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Виберіть спочатку пацієнта!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton goButton = new JButton("Перейти");
		goButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		paginationPanel.setLayout(new FlowLayout());
		paginationPanel.add(nextButton, BorderLayout.WEST);
		paginationPanel.add(goPanel, BorderLayout.WEST);
		paginationPanel.add(goButton, BorderLayout.WEST);
		paginationPanel.add(prevButton, BorderLayout.WEST);
		paginationPanel.add(editButton, BorderLayout.WEST);
		paginationPanel.add(removeButton, BorderLayout.WEST);

		jFrame.add(paginationPanel, BorderLayout.SOUTH);
	}

	private Object[][] getData(List<Patient> patients) {
		Object[][] array = new Object[patients.size()][columnsHeader.length];

		for (int i = 0; i < patients.size(); i++) {
			array[i][0] = patients.get(i).getSurname();
			array[i][1] = patients.get(i).getName();
			array[i][2] = patients.get(i).getPhone();
		}

		return array;
	}
}
