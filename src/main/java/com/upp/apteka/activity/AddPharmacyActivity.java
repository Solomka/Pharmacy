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

import com.upp.apteka.bo.Pharmacy;
import com.upp.apteka.config.Mapper;
import com.upp.apteka.service.PharmacyService;
import com.upp.apteka.validator.ValidationError;

@Component("addPharmacyActivity")
@Scope("prototype")
public class AddPharmacyActivity implements Activity {

	@Autowired
	private JFrame jFrame;

	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 35;

	private static final int BORDER = 40;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	@Autowired
	private Mapper mapper;

	@Autowired
	private PharmacyService pharmacyService;

	private Pharmacy editPharmacy;

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

		JTextField address = new JTextField();
		address.setFont(font);
		address.setName("form:address");

		NumberFormatter nf = new NumberFormatter();
		nf.setMinimum(1);
		nf.setMaximum(100);
		JTextField extra = new JFormattedTextField(nf);
		extra.setFont(font);
		extra.setName("form:extra");

		// init labels for textFields
		JLabel nameLabel = new JLabel("Назва:");
		nameLabel.setFont(font);

		JLabel addressLabel = new JLabel("Адреса:");
		addressLabel.setFont(font);

		JLabel extraLabel = new JLabel("Націнка");
		extraLabel.setFont(font);

		// init empty labels for future input errors
		JLabel nameError = new JLabel();
		nameError.setName("error:name");
		nameError.setForeground(Color.RED);
		nameError.setFont(font);

		JLabel addressError = new JLabel();
		addressError.setName("error:address");
		addressError.setForeground(Color.RED);
		addressError.setFont(font);

		JLabel extraError = new JLabel();
		extraError.setName("error:extra");
		extraError.setForeground(Color.RED);
		extraError.setFont(font);

		JPanel buttonPanel = new JPanel();

		// init button
		JButton button = new JButton("Додати");
		button.setFont(font);
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		/*
		 * check if edit or add pharmacy
		 */
		editPharmacy = (Pharmacy) params.get("pharmacy");

		if (editPharmacy != null) {
			button.setText("Редагувати");

			name.setText(editPharmacy.getName());
			address.setText(editPharmacy.getAddress());
			extra.setText(String.valueOf(editPharmacy.getExtra()));

			// don't allow to change pharmacy extra
			extra.setEnabled(false);

		}

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					if (editPharmacy == null) {
						List<ValidationError> list = pharmacyService.processAdding(jFrame);
						if (list.size() == 0) {
							mapper.changeActivity("addPharmacy", new HashMap<String, Object>());

							JOptionPane.showMessageDialog(jFrame, "Успішно додано аптеку!", "Успішна операція",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						List<ValidationError> list = pharmacyService.processEditing(jFrame, editPharmacy.getId());

						if (list.size() == 0) {
							mapper.changeActivity("addPharmacy", new HashMap<String, Object>());

							JOptionPane.showMessageDialog(jFrame, "Успішно змінено інформацію про аптеку!",
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

		JPanel addressPanel = new JPanel();

		addressPanel.setLayout(new BorderLayout());
		addressPanel.add(addressLabel, BorderLayout.WEST);
		addressPanel.add(addressError, BorderLayout.EAST);

		jFrame.add(addressPanel);
		jFrame.add(address);

		JPanel extraPanel = new JPanel();

		extraPanel.setLayout(new BorderLayout());
		extraPanel.add(extraLabel, BorderLayout.WEST);
		extraPanel.add(extraError, BorderLayout.EAST);

		jFrame.add(extraPanel);
		jFrame.add(extra);

		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());

		buttonPanel.add(button);
		jFrame.add(buttonPanel);
	}

}
