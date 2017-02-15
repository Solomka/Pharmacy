package com.upp.apteka.component.buy.form;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

public class BuyInputForm extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel medicineLabel;
	private JTextField numberField;
	private JLabel quantityLabel;

	private Long medicineId;
	
	public static final int HEIGHT = 20;

	public BuyInputForm(Long medicineId, String medicineName, int packBought, int packAvailable,
			int medicineAvailable) {
		setLayout(new FlowLayout());
		this.medicineId = medicineId;
		medicineLabel = new JLabel(medicineName);
		medicineLabel.setPreferredSize(new Dimension(150, HEIGHT));
		medicineLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel inputPanel = new JPanel();

		NumberFormatter nf = new NumberFormatter();
		nf.setMinimum(0);
		nf.setMaximum(
				(packAvailable - packBought > medicineAvailable) ? medicineAvailable : (packAvailable - packBought));
		numberField = new JFormattedTextField(nf);
		numberField.setPreferredSize(new Dimension(100, HEIGHT));

		// TODO Add constants

		quantityLabel = new JLabel(packBought + "/" + packAvailable + "(" + medicineAvailable + ")");
		quantityLabel.setPreferredSize(new Dimension(100, HEIGHT));
		add(medicineLabel);

		inputPanel.add(numberField);
		inputPanel.add(quantityLabel);
		add(inputPanel);
		
		if(medicineAvailable == 0)
			numberField.setEditable(false);
	}

	public Integer getNumber() {
		if (numberField.getText() == null || numberField.getText().equals(""))
			return null;
		return Integer.parseInt(numberField.getText());
	}

	public Long getMedicineId() {
		return medicineId;
	}
}
