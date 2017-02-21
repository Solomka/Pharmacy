package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import com.upp.apteka.layout.ModifiedFlowLayout;

@Component("viewPurchaseActivity")
@Scope("prototype")
public class ViewPurchaseActivity implements Activity {

	@Autowired
	private JFrame frame;

	private List<BuyInputForm> forms;

	private JLabel totalPriceLabel;

	@Autowired
	private Pharmacy pharmacy;

	private Purchase viewPurchase;
	private Prescription prescription;

	private static final int WINDOW_BORDER = 20;

	public void showActivity(final Map<String, Object> params) {

		prescription = (Prescription) params.get("prescription");
		viewPurchase = (Purchase) params.get("purchase");

		frame.setContentPane(new JPanel());
		frame.setLayout(new BorderLayout());

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

		totalPriceLabel = new JLabel("<html><b>Вартість</b>: 0");
		infoPanel.add(totalPriceLabel);

		forms = new ArrayList<BuyInputForm>();

		mainPanel.add(infoPanel);

		contentPanel.add(mainPanel, BorderLayout.NORTH);

		List<PurchaseMedicine> purchaseMedicines = new ArrayList<PurchaseMedicine>();

		purchaseMedicines = viewPurchase.getPurchaseMedicines();

		outer: for (PrescriptionMedicine prescriptionMedicine : prescription.getPrescriptionMedicines()) {
			List<PharmacyMedicine> pharmacyMedicines = prescriptionMedicine.getMedicine().getPharmacyMedicines();

			for (PharmacyMedicine pharmacyMedicine : pharmacyMedicines) {
				if (pharmacyMedicine.getPharmacy().getId() == pharmacy.getId()) {

					int bought = 0;

					for (PurchaseMedicine purchaseMedicine : purchaseMedicines)
						if (purchaseMedicine.getMedicine().getId() == pharmacyMedicine.getMedicine().getId())
							bought = purchaseMedicine.getPackQuantity();

					BuyInputForm buy = new BuyInputForm(prescriptionMedicine.getMedicine().getId(),
							prescriptionMedicine.getMedicine().getName() + " "
									+ prescriptionMedicine.getMedicine().getProducer(),
							prescriptionMedicine.getPackBought(), prescriptionMedicine.getPackQuantity(),
							pharmacyMedicine.getPackQuantity(), pharmacyMedicine.getPackPrice());

					if (bought != 0)
						buy.getNumberField().setText(String.valueOf(bought));
					forms.add(buy);

					buy.getNumberField().setEnabled(false);
					continue outer;
				}
			}
			forms.add(new BuyInputForm(prescriptionMedicine.getMedicine().getId(),
					prescriptionMedicine.getMedicine().getName() + " "
							+ prescriptionMedicine.getMedicine().getProducer(),
					prescriptionMedicine.getPackBought(), prescriptionMedicine.getPackQuantity(), 0,
					new BigDecimal(-1)));

		}

		updatePrice();

		JPanel parentPanel = new JPanel();
		parentPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		parentPanel.setLayout(new GridLayout(0, 1));

		JPanel fieldsInputPanel = new JPanel();
		fieldsInputPanel.setLayout(new ModifiedFlowLayout());

		JScrollPane scroll = new JScrollPane(fieldsInputPanel);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));

		for (BuyInputForm form : forms)
			fieldsInputPanel.add(form);

		parentPanel.add(scroll);
		contentPanel.add(parentPanel);

		frame.add(contentPanel);
	}

	protected void updatePrice() {
		try {

			double totalPrice = 0;

			for (BuyInputForm buyInputForm : forms) {
				Integer value;

				try {
					value = buyInputForm.getNumber();
				} catch (Exception numE) {
					value = 0;
				}

				if (value != null && value > 0) {
					totalPrice += buyInputForm.getPrice().doubleValue() * value;
				}

			}

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			nf.setRoundingMode(RoundingMode.HALF_UP);

			totalPriceLabel.setText("<html><b>Вартість</b>: " + nf.format(totalPrice));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
