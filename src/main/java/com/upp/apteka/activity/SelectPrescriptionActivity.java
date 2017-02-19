package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.Prescription;
import com.upp.apteka.component.combobox.searchable.SearchableComboBox;
import com.upp.apteka.component.combobox.searchable.SearchableItem;
import com.upp.apteka.config.Mapper;
import com.upp.apteka.service.PrescriptionService;
import com.upp.apteka.service.searchable.SearchablePatientService;

@Component("selectPrescriptionActivity")
@Scope("prototype")
public class SelectPrescriptionActivity implements Activity {

	@Autowired
	private SearchablePatientService searchablePatientService;

	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private JFrame frame;

	@Autowired
	private Mapper mapper;

	private static final int SELECT_WIDTH = 300;
	private static final int SELECT_HEIGHT = 35;

	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 35;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	private static final int LIST_BORDER_HORIZONTAL = 80;
	private static final int LIST_BORDER_VERTICAL = 40;

	public void showActivity(Map<String, Object> params) {
		frame.setLayout(new BorderLayout());

		JPanel patientPanel = new JPanel();
		patientPanel.setLayout(new FlowLayout());

		JLabel patientLabel = new JLabel("Пацієнт: ");
		patientLabel.setFont(font);

		final SearchableComboBox searchableComboBox = new SearchableComboBox(searchablePatientService);
		searchableComboBox.setPreferredSize(new Dimension(SELECT_WIDTH, SELECT_HEIGHT));
		searchableComboBox.setFont(font);

		JButton selectPatientButton = new JButton("Вибрати");
		selectPatientButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		patientPanel.add(patientLabel);
		patientPanel.add(searchableComboBox);
		patientPanel.add(selectPatientButton);

		frame.add(patientPanel, BorderLayout.NORTH);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder(LIST_BORDER_VERTICAL, LIST_BORDER_HORIZONTAL,
				LIST_BORDER_VERTICAL, LIST_BORDER_HORIZONTAL));

		final DefaultListModel<SearchableItem> model = new DefaultListModel<SearchableItem>();

		final JList<SearchableItem> list = new JList<SearchableItem>(model);
		list.setFont(font);

		JScrollPane pane = new JScrollPane(list);

		JButton chooseButton = new JButton("Обрати");
		chooseButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		JPanel buttonPanel = new JPanel();

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(LIST_BORDER_VERTICAL, 0, 0, 0));
		buttonPanel.add(chooseButton);

		listPanel.add(pane, BorderLayout.CENTER);
		listPanel.add(buttonPanel, BorderLayout.SOUTH);

		selectPatientButton.setFont(font);
		selectPatientButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				SearchableItem item = (SearchableItem) searchableComboBox.getSelectedItem();

				if (item != null) {
					List<Prescription> prescriptions = prescriptionService.getUnboughtPrescriptions(item.getId());
					model.removeAllElements();

					for (Prescription prescription : prescriptions) {
						SearchableItem prescriptionItem = new SearchableItem(prescription.getId(),
								prescription.getDate() + " " + prescription.getDoctor().getSurname() + " "
										+ prescription.getDoctor().getName());
						model.addElement(prescriptionItem);
					}
				}

			}
		});

		chooseButton.setFont(font);
		chooseButton.addActionListener(new ActionListener() {

			//@Override
			public void actionPerformed(ActionEvent e) {
				SearchableItem item = list.getSelectedValue();

				if (item != null) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("prescriptionId", item.getId());

					mapper.changeActivity("addPurchase", params);
				}

			}
		});

		frame.add(listPanel, BorderLayout.CENTER);
	}

}
