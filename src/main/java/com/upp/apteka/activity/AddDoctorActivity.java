package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
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

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.config.Mapper;
import com.upp.apteka.service.DoctorService;
import com.upp.apteka.validator.ValidationError;

@Component("addDoctorActivity")
@Scope("prototype")
public class AddDoctorActivity implements Activity {

	@Autowired
	private JFrame jFrame;

	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 35;

	private static final int BORDER = 40;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	@Autowired
	private Mapper mapper;

	@Autowired
	private DoctorService doctorService;

	private Doctor editDoctor;

	public void showActivity(Map<String, Object> params) {

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(BORDER / 3, BORDER, BORDER / 3, BORDER));
		jFrame.setContentPane(mainPanel);
		jFrame.getContentPane().setLayout(new GridLayout(0, 1));

		JPanel namePanel = new JPanel();

		JTextField name = new JTextField();
		name.setFont(font);
		name.setName("form:name");

		JTextField surname = new JTextField();
		surname.setFont(font);
		surname.setName("form:surname");

		JTextField occupation = new JTextField();
		occupation.setFont(font);
		occupation.setName("form:occupation");

		Calendar now = Calendar.getInstance(); // Gets the current date and time
		int year = now.get(Calendar.YEAR);

		NumberFormatter nf = new NumberFormatter();
		nf.setMinimum(1900);
		nf.setMaximum(year);
		JTextField standing = new JFormattedTextField(nf);
		standing.setFont(font);
		standing.setName("form:standing");

		// init labels for textFields
		JLabel nameLabel = new JLabel("Імя:");
		nameLabel.setFont(font);

		JLabel surnameLabel = new JLabel("Прізвище:");
		surnameLabel.setFont(font);

		JLabel occupationLabel = new JLabel("Спеціальність:");
		occupationLabel.setFont(font);

		JLabel standingLabel = new JLabel("Стаж (вказати рік [1900-" + year + "]):");
		standingLabel.setFont(font);

		// init empty labels for future input errors
		JLabel nameError = new JLabel();
		nameError.setName("error:name");
		nameError.setForeground(Color.RED);
		nameError.setFont(font);

		JLabel surnameError = new JLabel();
		surnameError.setName("error:surname");
		surnameError.setForeground(Color.RED);
		surnameError.setFont(font);

		JLabel occupationError = new JLabel();
		occupationError.setName("error:occupation");
		occupationError.setForeground(Color.RED);
		occupationError.setFont(font);

		JLabel standingError = new JLabel();
		standingError.setName("error:standing");
		standingError.setForeground(Color.RED);
		standingError.setFont(font);

		JPanel buttonPanel = new JPanel();

		// init button
		JButton button = new JButton("Додати");
		button.setFont(font);
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		if (params != null)
			editDoctor = (Doctor) params.get("doctor");

		if (editDoctor != null) {
			button.setText("Редагувати");
			
			name.setText(editDoctor.getName());
			surname.setText(editDoctor.getSurname());
			occupation.setText(editDoctor.getOccupation());
			standing.setText(String.valueOf(editDoctor.getStanding()));
		}

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					if (editDoctor == null) {
						List<ValidationError> list = doctorService.processAdding(jFrame);

						if (list.size() == 0) {
							mapper.changeActivity("addDoctor", null);

							JOptionPane.showMessageDialog(jFrame, "Успішно додано лікаря!", "Успішна операція",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						List<ValidationError> list = doctorService.processEditing(jFrame, editDoctor.getId());

						if (list.size() == 0) {
							mapper.changeActivity("addDoctor", null);

							JOptionPane.showMessageDialog(jFrame, "Успішно змінено інформацію про лікаря!",
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

		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(nameError, BorderLayout.EAST);

		/*
		 * first add JLabel(name/error) second add JTextField
		 */
		jFrame.add(namePanel);
		jFrame.add(name);

		JPanel surnamePanel = new JPanel();

		surnamePanel.setLayout(new BorderLayout());
		surnamePanel.add(surnameLabel, BorderLayout.WEST);
		surnamePanel.add(surnameError, BorderLayout.EAST);

		jFrame.add(surnamePanel);
		jFrame.add(surname);

		JPanel occupationPanel = new JPanel();

		occupationPanel.setLayout(new BorderLayout());
		occupationPanel.add(occupationLabel, BorderLayout.WEST);
		occupationPanel.add(occupationError, BorderLayout.EAST);

		JPanel standingPanel = new JPanel();

		standingPanel.setLayout(new BorderLayout());
		standingPanel.add(standingLabel, BorderLayout.WEST);
		standingPanel.add(standingError, BorderLayout.EAST);

		jFrame.add(occupationPanel);
		jFrame.add(occupation);

		jFrame.add(standingPanel);
		jFrame.add(standing);

		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());

		buttonPanel.add(button);
		jFrame.add(buttonPanel);
	}

}
