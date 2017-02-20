package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.config.Mapper;
import com.upp.apteka.service.MedicineService;
import com.upp.apteka.validator.ValidationError;

@Component("addMedicineActivity")
@Scope("prototype")
public class AddMedicineActivity implements Activity {

	@Autowired
	private JFrame jFrame;

	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 35;

	private static final int BORDER = 40;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	@Autowired
	private Mapper mapper;

	@Autowired
	private MedicineService medicineService;

	private Medicine editMedicine;

	public void showActivity(Map<String, Object> params) {
		// main JFramePanel
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(BORDER / 3, BORDER, BORDER / 3, BORDER));

		jFrame.setContentPane(mainPanel);
		jFrame.getContentPane().setLayout(new GridLayout(0, 1));

		// text fields

		JTextField name = new JTextField();
		name.setFont(font);
		name.setName("form:name");

		JTextField producer = new JTextField();
		producer.setFont(font);
		producer.setName("form:producer");

		
		JTextField boxPrice = new JTextField();
		boxPrice.setFont(font);
		boxPrice.setName("form:boxPrice");

		// don't allow to change medicine boxPrice
		if (editMedicine != null) {
			boxPrice.setEnabled(false);
		}

		NumberFormatter nf2 = new NumberFormatter();
		nf2.setMinimum(1);
		nf2.setMaximum(100000);
		JTextField quantityPerBox = new JFormattedTextField(nf2);
		quantityPerBox.setFont(font);
		quantityPerBox.setName("form:quantityPerBox");

		// init labels for textFields
		JLabel nameLabel = new JLabel("Назва:");
		nameLabel.setFont(font);

		JLabel producerLabel = new JLabel("Виробник:");
		producerLabel.setFont(font);

		JLabel boxPriceLabel = new JLabel("Ціна за коробоку:");
		boxPriceLabel.setFont(font);

		JLabel quantityPerBoxLabel = new JLabel("Кількість упаковок у коробці:");
		quantityPerBoxLabel.setFont(font);

		// init empty labels for future input errors
		JLabel nameError = new JLabel();
		nameError.setName("error:name");
		nameError.setForeground(Color.RED);
		nameError.setFont(font);

		JLabel producerError = new JLabel();
		producerError.setName("error:producer");
		producerError.setForeground(Color.RED);
		producerError.setFont(font);

		JLabel boxPriceError = new JLabel();
		boxPriceError.setName("error:boxPrice");
		boxPriceError.setForeground(Color.RED);
		boxPriceError.setFont(font);

		JLabel quantityPerBoxError = new JLabel();
		quantityPerBoxError.setName("error:quantityPerBox");
		quantityPerBoxError.setForeground(Color.RED);
		quantityPerBoxError.setFont(font);

		JPanel buttonPanel = new JPanel();

		// init button
		JButton button = new JButton("Додати");
		button.setFont(font);
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		/*
		 * check if edit or add pharmacy
		 */
		editMedicine = (Medicine) params.get("medicine");

		if (editMedicine != null) {
			button.setText("Редагувати");

			name.setText(editMedicine.getName());
			producer.setText(editMedicine.getProducer());
			boxPriceLabel.setText(String.valueOf(editMedicine.getBoxPrice()));
			quantityPerBox.setText(String.valueOf(editMedicine.getQuantityPerBox()));
		}

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					if (editMedicine == null) {						
						List<ValidationError> list = medicineService.processAdding(jFrame);
						if (list.size() == 0) {
							System.out.println("We are here");
							mapper.changeActivity("addMedicine", new HashMap<String, Object>());

							JOptionPane.showMessageDialog(jFrame, "Успішно додано ліки!", "Успішна операція",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						List<ValidationError> list = medicineService.processEditing(jFrame, editMedicine.getId());

						if (list.size() == 0) {
							mapper.changeActivity("addMedicine", new HashMap<String, Object>());

							JOptionPane.showMessageDialog(jFrame, "Успішно змінено інформацію про ліки!",
									"Успішна операція", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (Exception addException) {
					JOptionPane.showMessageDialog(jFrame,
							new String[] { "Сервіс тимчасово недоступний. Спробуйте, будь ласка, пізніше." }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});

		/*
		 * namePanel: - layout - JLabel nameLabel - JLabel errorLabel
		 */
		JPanel namePanel = new JPanel();

		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(nameError, BorderLayout.EAST);

		/*
		 * first add JLabel(name/error) second add JTextField
		 */
		jFrame.add(namePanel);
		jFrame.add(name);

		JPanel producerPanel = new JPanel();

		producerPanel.setLayout(new BorderLayout());
		producerPanel.add(producerLabel, BorderLayout.WEST);
		producerPanel.add(producerError, BorderLayout.EAST);

		jFrame.add(producerPanel);
		jFrame.add(producer);

		JPanel boxPricePanel = new JPanel();

		boxPricePanel.setLayout(new BorderLayout());
		boxPricePanel.add(boxPriceLabel, BorderLayout.WEST);
		boxPricePanel.add(boxPriceError, BorderLayout.EAST);

		jFrame.add(boxPricePanel);
		jFrame.add(boxPrice);

		JPanel quantityPerBoxPanel = new JPanel();

		quantityPerBoxPanel.setLayout(new BorderLayout());
		quantityPerBoxPanel.add(quantityPerBoxLabel, BorderLayout.WEST);
		quantityPerBoxPanel.add(quantityPerBoxError, BorderLayout.EAST);

		jFrame.add(quantityPerBoxPanel);
		jFrame.add(quantityPerBox);

		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());

		buttonPanel.add(button);
		jFrame.add(buttonPanel);
	}

}
