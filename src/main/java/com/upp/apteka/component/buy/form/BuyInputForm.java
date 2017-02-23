package com.upp.apteka.component.buy.form;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigDecimal;

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
	private JLabel priceLabel;

	private Long medicineId;
	private BigDecimal price;

	public static final int HEIGHT = 20;

	public BuyInputForm(Long medicineId, String medicineName, int packBought, int packAvailable, int medicineAvailable,
			BigDecimal price) {
		this.setPrice(price);
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

		if (price.intValue() != -1)
			priceLabel = new JLabel(price + " грн");
		else
			priceLabel = new JLabel("Таких ліків немає у мережі аптек");

		priceLabel.setPreferredSize(new Dimension(200, HEIGHT));

		add(medicineLabel);

		inputPanel.add(numberField);
		inputPanel.add(quantityLabel);
		add(inputPanel);
		add(priceLabel);

		if (medicineAvailable == 0 || packBought == packAvailable)
			numberField.setEditable(false);
	}

	public Integer getNumber() {

		String value = numberField.getText().replace(" ", "");

		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return null;
		}
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public JTextField getNumberField() {
		return numberField;
	}
}
