package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.component.combobox.searchable.SearchableComboBox;
import com.upp.apteka.component.combobox.searchable.SearchableItem;
import com.upp.apteka.dto.ChooseMedicineDto;
import com.upp.apteka.service.PrescriptionService;
import com.upp.apteka.service.searchable.SearchableDoctorService;
import com.upp.apteka.service.searchable.SearchableMedicineService;
import com.upp.apteka.service.searchable.SearchablePatientService;

@Component
@Scope("prototype")
public class AddPrescriptionActivity {

	private List<ChooseMedicineDto> selectedItems;

	private JList<SearchableItem> list;

	private static final int INPUT_WIDTH = 500;
	private static final int INPUT_HEIGHT = 25;

	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;

	private static final String DATE_FORMAT = "MM/dd/yyyy";
	
	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private JFrame jFrame;

	@Autowired
	private SearchableDoctorService searchableDoctorService;

	@Autowired
	private SearchablePatientService searchablePatientService;

	@Autowired
	private SearchableMedicineService searchableMedicineService;

	private DefaultListModel<SearchableItem> defaultListModel;

	public void showActivity() {

		selectedItems = new ArrayList<ChooseMedicineDto>();

		JPanel mainPanel = new JPanel();
		jFrame.setContentPane(mainPanel);
		jFrame.getContentPane().setLayout(new FlowLayout());

		/**
		 * Головні панелі (з бордером)
		 */
		JPanel mainFieldsPanel = new JPanel();
		Border fieldsBorder = BorderFactory.createTitledBorder("Дані лікаря/пацієнта");
		mainFieldsPanel.setBorder(fieldsBorder);

		JPanel mainListPanel = new JPanel();
		Border listBorder = BorderFactory.createTitledBorder("Дані про ліки");
		mainListPanel.setBorder(listBorder);

		/**
		 * Панелі-контейнери
		 */
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		fieldsPanel.setLayout(new GridLayout(0, 1, 0, 5));

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 20));

		/**
		 * Вміст контейнера "Дані лікаря/пацієнта"
		 */

		JPanel doctorPanel = new JPanel();
		doctorPanel.setLayout(new GridLayout(0, 1, 0, 5));
		doctorPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		JPanel patientPanel = new JPanel();
		patientPanel.setLayout(new GridLayout(0, 1, 0, 5));
		patientPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridLayout(0, 1, 0, 5));
		datePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		/**
		 * Вміст другого контейнера
		 */

		JPanel medicinePanel = new JPanel();
		medicinePanel.setLayout(new GridLayout(0, 1, 0, 5));
		medicinePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		defaultListModel = new DefaultListModel<SearchableItem>();
		list = new JList<SearchableItem>(defaultListModel);
		JScrollPane pane = new JScrollPane(list);
		
		/**
		 * Блок кнопок додати/видалити
		 */
		
		JButton addButton = new JButton("Додати");
		JButton removeButton = new JButton("Видалити");

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(addButton);
		buttonsPanel.add(removeButton);
		
		/**
		 * Блок кількості
		 */
		JLabel quantityLabel = new JLabel("Кількість: ");
		
		NumberFormatter nf = new NumberFormatter();
		nf.setMinimum(0);
		final JFormattedTextField textField = new JFormattedTextField(nf);
		textField.setColumns(5);
		textField.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		JPanel quantityPanel = new JPanel();
		quantityPanel.add(quantityLabel);
		quantityPanel.add(textField);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(quantityPanel, BorderLayout.WEST);
		controlPanel.add(buttonsPanel, BorderLayout.EAST);

		JLabel dateLabel = new JLabel("Введіть дату");
		JLabel doctorLabel = new JLabel("Виберіть лікаря:");
		JLabel patientLabel = new JLabel("Виберіть пацієнта:");
		JLabel medicineLabel = new JLabel("Виберіть ліки:");
		
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		final JFormattedTextField txtDate = new JFormattedTextField(df);
		
		final JComboBox<SearchableItem> searchDoctor = new SearchableComboBox(searchableDoctorService);
		searchDoctor.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		
		final JComboBox<SearchableItem> searchPatient = new SearchableComboBox(searchablePatientService);
		searchPatient.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		
		final JComboBox<SearchableItem> searchMedicine = new SearchableComboBox(searchableMedicineService);
		searchMedicine.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));

		doctorPanel.add(doctorLabel);
		doctorPanel.add(searchDoctor);

		patientPanel.add(patientLabel);
		patientPanel.add(searchPatient);

		datePanel.add(dateLabel);
		datePanel.add(txtDate);

		medicinePanel.add(medicineLabel);
		medicinePanel.add(searchMedicine);

		fieldsPanel.add(doctorPanel);
		fieldsPanel.add(patientPanel);
		fieldsPanel.add(datePanel);

		listPanel.add(medicinePanel, BorderLayout.NORTH);
		listPanel.add(pane, BorderLayout.CENTER);
		listPanel.add(controlPanel, BorderLayout.SOUTH);

		addButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				SearchableItem item = (SearchableItem) searchMedicine.getSelectedItem();

				Integer quantity = null;

				try {
					quantity = Integer.valueOf(textField.getText());
				} catch (Exception exception) {

				}
				if (item != null && quantity != null && quantity > 1) {
					ChooseMedicineDto dto = new ChooseMedicineDto();
					dto.setMedicineId(item.getId());
					dto.setQuantity(quantity);
					dto.setName(item.getValue());

					addToSelectedItems(dto);
					drawSelectedItems();

				} else {
					JOptionPane.showMessageDialog(jFrame,
							new String[] { "Можливі помилки :", " - невибрано ліки", " - невказано кількість" },
							"Помилка", JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		removeButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		removeButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				SearchableItem item = (SearchableItem) list.getSelectedValue();

				if (item != null) {
					removeItem(item);
					drawSelectedItems();
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Необрано лікарство" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		mainFieldsPanel.add(fieldsPanel);
		jFrame.add(mainFieldsPanel);

		mainListPanel.add(listPanel);
		jFrame.add(mainListPanel);

		JButton submitButton = new JButton("Створити");
		JButton resetButton = new JButton("Відновити");

		jFrame.add(submitButton);
		jFrame.add(resetButton);

		submitButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				SearchableItem doctor = (SearchableItem) searchDoctor.getSelectedItem();
				SearchableItem patient = (SearchableItem) searchPatient.getSelectedItem();
				String text = txtDate.getText();
				
				if (doctor != null && patient != null && text != null && !text.equals("") && !selectedItems.isEmpty()) {
					Long doctorId = doctor.getId();
					Long patientId = patient.getId();

					DateFormat format = new SimpleDateFormat(DATE_FORMAT);
					try {
						java.util.Date date = format.parse(text);
						Date sqlDate = new Date(date.getTime());
						
						prescriptionService.create(doctorId, patientId, sqlDate, selectedItems);
						
					} catch (ParseException parseException) {
						JOptionPane.showMessageDialog(jFrame, new String[] { "Некоректна дата!" }, "Помилка",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Потрібно заповнити всі поля!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void drawSelectedItems() {
		defaultListModel.clear();
		for (ChooseMedicineDto item : selectedItems)
			defaultListModel
					.addElement(new SearchableItem(item.getMedicineId(), item.getName() + " x" + item.getQuantity()));
	}

	private void addToSelectedItems(ChooseMedicineDto dto) {
		for (ChooseMedicineDto item : selectedItems) {
			if (item.getMedicineId() == dto.getMedicineId()) {
				item.setQuantity(item.getQuantity() + dto.getQuantity());
				return;
			}
		}

		selectedItems.add(dto);
	}

	private void removeItem(SearchableItem item) {
		int index = -1;

		for (int i = 0; i < selectedItems.size(); i++)
			if (item.getId() == selectedItems.get(i).getMedicineId())
				index = i;

		selectedItems.remove(index);
	}
}
