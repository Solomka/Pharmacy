package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.upp.apteka.config.Mapper;
import com.upp.apteka.dto.ChooseMedicineDto;
import com.upp.apteka.service.DeliveryService;
import com.upp.apteka.service.searchable.SearchableMedicineService;

@Component("addDeliveryActivity")
@Scope("prototype")
public class AddDeliveryActivity implements Activity {

	private List<ChooseMedicineDto> selectedItems;

	private JList<SearchableItem> list;

	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;

	private static final int MIN_MEDICINE_PANEL_HEIGHT = 80;

	private static final String DATE_FORMAT = "MM/dd/yyyy";

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private JFrame jFrame;

	@Autowired
	private Mapper mapper;

	@Autowired
	private SearchableMedicineService searchableMedicineService;

	private DefaultListModel<SearchableItem> defaultListModel;

	private static final int WINDOW_BORDER = 20;

	public void showActivity(Map<String, Object> params) {

		selectedItems = new ArrayList<ChooseMedicineDto>();

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel
				.setBorder(BorderFactory.createEmptyBorder(WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER));

		jFrame.setContentPane(contentPanel);

		/**
		 * Main panels (with borders)
		 */

		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new GridLayout(0, 1, 10, 10));

		JPanel mainFieldsPanel = new JPanel();
		Border fieldsBorder = BorderFactory.createTitledBorder("Дані про дату (MM/dd/yyyy)");
		mainFieldsPanel.setLayout(new GridLayout(0, 1));
		mainFieldsPanel.setBorder(fieldsBorder);
		//mainFieldsPanel.setMaximumSize(new Dimension(175, 175));
		//mainFieldsPanel.setPreferredSize(new Dimension(175, 175));
		

		JPanel mainListPanel = new JPanel();
		mainListPanel.setLayout(new BorderLayout());
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
		 * Date container content
		 */

		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridLayout(0, 1, 0, 5));
		datePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		/**
		 * Medicine container content
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
		JLabel quantityLabel = new JLabel("Кількість коробок: ");

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

		JLabel dateLabel = new JLabel("Введіть дату:");
		JLabel medicineLabel = new JLabel("Виберіть ліки:");

		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		final JFormattedTextField txtDate = new JFormattedTextField(df);

		final JComboBox<SearchableItem> searchMedicine = new SearchableComboBox(searchableMedicineService);

		datePanel.add(dateLabel);
		datePanel.add(txtDate);

		medicinePanel.setPreferredSize(new Dimension(0, MIN_MEDICINE_PANEL_HEIGHT));
		medicinePanel.add(medicineLabel);
		medicinePanel.add(searchMedicine);
		
		/*
		 * Ty tupe dno. Love you =)
		 * hovnocode
		 */
		JPanel emptyPanel = new JPanel();
		emptyPanel.setLayout(new GridLayout(0, 1, 0, 5));
		emptyPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		JPanel emptyPanel2 = new JPanel();
		emptyPanel2.setLayout(new GridLayout(0, 1, 0, 5));
		emptyPanel2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));


		fieldsPanel.add(datePanel);
		fieldsPanel.add(emptyPanel);
		fieldsPanel.add(emptyPanel2);

		listPanel.add(medicinePanel, BorderLayout.NORTH);
		listPanel.add(pane, BorderLayout.CENTER);
		listPanel.add(controlPanel, BorderLayout.SOUTH);

		addButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addButton.addActionListener(new ActionListener() {

			// @Override
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
							new String[] { "Можливі помилки :", " - не обрано ліки", " - не вказано кількість" },
							"Помилка", JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		removeButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		removeButton.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				SearchableItem item = (SearchableItem) list.getSelectedValue();

				if (item != null) {
					removeItem(item);
					drawSelectedItems();
				} else {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Не обрано ліки" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		mainFieldsPanel.add(fieldsPanel);
		parentPanel.add(mainFieldsPanel);

		mainListPanel.add(listPanel);
		parentPanel.add(mainListPanel);

		jFrame.add(parentPanel);

		JPanel proceedPanel = new JPanel();
		JButton submitButton = new JButton("Створити");

		proceedPanel.add(submitButton);

		jFrame.add(proceedPanel, BorderLayout.SOUTH);

		submitButton.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {

				String text = txtDate.getText();

				if (text != null && !text.equals("") && !selectedItems.isEmpty()) {

					DateFormat format = new SimpleDateFormat(DATE_FORMAT);
					try {
						java.util.Date date = format.parse(text);
						Date sqlDate = new Date(date.getTime());

						try {

							deliveryService.addDelivery(sqlDate, selectedItems);

						} catch (Exception addingError) {
							addingError.printStackTrace();
							JOptionPane.showMessageDialog(jFrame,
									new String[] { "Сервіс тимчасово недоступний. Спробуйте, будь ласка, пізніше." },
									"Помилка", JOptionPane.ERROR_MESSAGE);
							return;
						}

						mapper.changeActivity("addDelivery", new HashMap<String, Object>());

						JOptionPane.showMessageDialog(jFrame, "Успішно додано поставку!", "Успішна операція",
								JOptionPane.INFORMATION_MESSAGE);

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
