package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.bo.PrescriptionMedicine;
import com.upp.apteka.bo.Purchase;
import com.upp.apteka.bo.PurchaseMedicine;
import com.upp.apteka.dto.MedicineShowDto;

@Component
@Scope("prototype")
public class ViewPrescriptionActivity implements Activity {

	@Autowired
	private JFrame jFrame;

	private Prescription prescription;
	private List<PrescriptionMedicine> prescriptionMedicines;

	private double total;

	private static final int WINDOW_BORDER = 20;

	private static final int ROW_HEIGHT = 32;
	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	//@Override
	public void showActivity(Map<String, Object> params) {

		prescription = (Prescription) params.get("prescription");
		prescriptionMedicines = prescription.getPrescriptionMedicines();

		Object[] headers = new Object[prescriptionMedicines.size() + 4];
		headers[0] = "Номер покупки";
		headers[1] = "Аптека";
		headers[2] = "Дата";

		List<Purchase> purchases = prescription.getPurchases();
		Object[][] data = new Object[purchases.size() + 2][headers.length];

		for (int i = 3; i < prescriptionMedicines.size() + 3; i++) {
			headers[i] = new MedicineShowDto(prescriptionMedicines.get(i - 3).getMedicine());
			data[data.length - 2][i] = prescriptionMedicines.get(i - 3).getPackBought();
			data[data.length - 1][i] = prescriptionMedicines.get(i - 3).getPackQuantity();
		}

		data[data.length - 2][0] = "Куплено";
		data[data.length - 1][0] = "Максимум";

		headers[headers.length - 1] = "Вартість";

		for (int i = 0; i < purchases.size(); i++) {
			data[i][0] = purchases.get(i).getId();
			data[i][1] = purchases.get(i).getPharmacy().getName();
			data[i][2] = purchases.get(i).getDate();
			double total = 0;

			for (PurchaseMedicine purchaseMedicine : purchases.get(i).getPurchaseMedicines()) {
				int columnIndex = getColumnIndex(purchaseMedicine.getMedicine().getId());

				List<PharmacyMedicine> pharmacyMedicines = purchaseMedicine.getMedicine().getPharmacyMedicines();

				double price = 0;
				for (PharmacyMedicine pharmacyMedicine : pharmacyMedicines)
					if (pharmacyMedicine.getPharmacy().getId() == purchases.get(i).getPharmacy().getId()) {
						price = pharmacyMedicine.getPackPrice().doubleValue();
						break;
					}
				total += price * purchaseMedicine.getPackQuantity();

				data[i][columnIndex] = purchaseMedicine.getPackQuantity() + " / " + price;
			}

			data[i][headers.length - 1] = total;
			this.total += total;
		}

		data[data.length - 2][headers.length - 1] = this.total;

		JTable purchasesTable = new JTable(data, headers);
		purchasesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		purchasesTable.setFont(font);
		purchasesTable.setRowHeight(ROW_HEIGHT);
		purchasesTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		purchasesTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JScrollPane scrollPane = new JScrollPane(purchasesTable);
		scrollPane.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
		// jFrame.add(scrollPane, BorderLayout.CENTER);

		JPanel contentPanel = new JPanel();
		contentPanel
				.setBorder(BorderFactory.createEmptyBorder(WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER));
		contentPanel.setLayout(new BorderLayout(WINDOW_BORDER, WINDOW_BORDER));

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createTitledBorder("Загальна інформація"));
		mainPanel.setLayout(new GridLayout(0, 1));

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0, WINDOW_BORDER, 0, WINDOW_BORDER));
		infoPanel.setLayout(new GridLayout(0, 1));

		infoPanel.add(new JLabel("<html><b>Покупець</b>: " + prescription.getPatient().getSurname() + " "
				+ prescription.getPatient().getName() + "<br/><b>Лікар</b>: " + prescription.getDoctor().getSurname()
				+ " " + prescription.getDoctor().getName() + "<br/><b>Дата</b>: " + prescription.getDate()
				+ "</html>"));

		mainPanel.add(infoPanel);
		contentPanel.add(mainPanel, BorderLayout.NORTH);

		JPanel parentPanel = new JPanel();
		parentPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		parentPanel.setLayout(new GridLayout(0, 1));

		JPanel tablePanel = new JPanel();
		tablePanel.add(scrollPane);
		tablePanel.setLayout(new GridLayout(0, 1));
		tablePanel.setBorder(BorderFactory.createEmptyBorder(WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER));
		contentPanel.add(parentPanel);

		jFrame.add(contentPanel, BorderLayout.NORTH);
		jFrame.add(tablePanel, BorderLayout.CENTER);
	}

	private int getColumnIndex(Long medicineId) {
		for (int i = 0; i < prescriptionMedicines.size(); i++)
			if (prescriptionMedicines.get(i).getMedicine().getId() == medicineId)
				return i + 3;

		return -1;
	}

}
