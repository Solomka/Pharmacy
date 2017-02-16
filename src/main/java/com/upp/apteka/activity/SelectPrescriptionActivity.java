package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

@Component
@Scope("prototype")
public class SelectPrescriptionActivity {

	@Autowired
	private SearchablePatientService searchablePatientService;

	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private JFrame frame;
	
	@Autowired
	private Mapper mapper;

	public void showActivity() {
		frame.setLayout(new BorderLayout());

		JPanel patientPanel = new JPanel();
		patientPanel.setLayout(new FlowLayout());

		JLabel patientLabel = new JLabel("Пацієнт: ");
		final SearchableComboBox searchableComboBox = new SearchableComboBox(searchablePatientService);
		JButton selectPatientButton = new JButton("Вибрати");

		patientPanel.add(patientLabel);
		patientPanel.add(searchableComboBox);
		patientPanel.add(selectPatientButton);

		frame.add(patientPanel, BorderLayout.NORTH);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
		final DefaultListModel<SearchableItem> model = new DefaultListModel<>();
		final JList<SearchableItem> list = new JList<SearchableItem>(model);
		JScrollPane pane = new JScrollPane(list);

		JButton chooseButton = new JButton("Обрати");
		JPanel buttonPanel = new JPanel();

		buttonPanel.add(chooseButton);

		listPanel.add(pane, BorderLayout.CENTER);
		listPanel.add(buttonPanel, BorderLayout.SOUTH);

		selectPatientButton.addActionListener(new ActionListener() {

			@Override
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

		chooseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SearchableItem item = list.getSelectedValue();
				
				if(item != null){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("prescriptionId", item.getId());
					
					mapper.changeActivity("addPurchase", params);
				}

			}
		});

		frame.add(listPanel, BorderLayout.CENTER);
	}

}
