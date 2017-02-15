package com.upp.apteka.activity;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.bo.PrescriptionMedicine;
import com.upp.apteka.bo.Purchase;
import com.upp.apteka.bo.PurchaseMedicine;
import com.upp.apteka.component.buy.form.BuyInputForm;
import com.upp.apteka.service.MedicineService;
import com.upp.apteka.service.PurchaseService;

@Component
@Scope("prototype")
public class AddPurchaseActivity {

	@Autowired
	private JFrame frame;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private MedicineService medicineService;

	private List<BuyInputForm> forms;

	public void showActivity(final Prescription prescription, final Pharmacy pharmacy) {

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createTitledBorder("Загальна інформація"));

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		infoPanel.setLayout(new GridLayout(0, 1));
		// TODO Винести константи
		infoPanel.setPreferredSize(new Dimension(540, 100));

		infoPanel.add(new JLabel(
				"Покупець: " + prescription.getPatient().getSurname() + " " + prescription.getPatient().getName()));
		infoPanel.add(new JLabel(
				"Лікар: " + prescription.getDoctor().getSurname() + " " + prescription.getDoctor().getName()));
		infoPanel.add(new JLabel("Дата: " + prescription.getDate()));

		forms = new ArrayList<>();

		frame.setContentPane(new JPanel());
		frame.setLayout(new FlowLayout());
		
		mainPanel.add(infoPanel);
		frame.add(mainPanel);

		outer: for (PrescriptionMedicine pm : prescription.getPrescriptionMedicines()) {
			List<PharmacyMedicine> pharmacyMedicines = pm.getMedicine().getPharmacyMedicines();

			for (PharmacyMedicine pharmMedicine : pharmacyMedicines) {
				if (pharmMedicine.getPharmacy().getId() == pharmacy.getId()) {

					forms.add(new BuyInputForm(pm.getMedicine().getId(),
							pm.getMedicine().getName() + " " + pm.getMedicine().getProducer(), pm.getPackBought(),
							pm.getPackQuantity(), pharmMedicine.getPackQuantity()));
					continue outer;
				}
			}
			forms.add(new BuyInputForm(pm.getMedicine().getId(),
					pm.getMedicine().getName() + " " + pm.getMedicine().getProducer(), pm.getPackBought(),
					pm.getPackQuantity(), 0));

		}

		JPanel fieldsInputPanel = new JPanel();
		fieldsInputPanel.setBorder(BorderFactory.createTitledBorder("Дані про покупку"));
		fieldsInputPanel.setLayout(new FlowLayout());
		fieldsInputPanel.setPreferredSize(new Dimension(560, BuyInputForm.HEIGHT * forms.size() + 60));

		for (BuyInputForm form : forms)
			fieldsInputPanel.add(form);

		frame.add(fieldsInputPanel);

		JButton submit = new JButton("Купити");

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<PurchaseMedicine> purchaseMedicines = new ArrayList<>();

				for (BuyInputForm bif : forms) {

					Integer value = bif.getNumber();

					if (value != null && value > 0) {
						PurchaseMedicine purchaseMedicine = new PurchaseMedicine();
						purchaseMedicine.setMedicine(medicineService.getMedicine(bif.getMedicineId()));
						purchaseMedicine.setPackQuantity(value);

						purchaseMedicines.add(purchaseMedicine);
					}

				}

				Purchase purchase = new Purchase();
				purchase.setDate(new Date(System.currentTimeMillis()));
				purchase.setPatient(prescription.getPatient());
				purchase.setPharmacy(pharmacy);
				purchase.setPrescription(prescription);
				purchase.setPurchaseMedicines(purchaseMedicines);

				if (purchaseMedicines.size() > 0) {
					purchaseService.create(purchase);
				}
			}
		});

		frame.add(submit);
	}

}
