package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.service.PatientService;
import javassist.NotFoundException;

@Component
@Scope("prototype")
public class AddPatientActivity implements Activity{
	
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 35;

	private static final int BORDER = 40;
	
	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	@Autowired
	private JFrame jFrame;

	@Autowired
	private PatientService patientService;

	public void showActivity() {
		
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

		JTextField phone = new JTextField();
		phone.setFont(font);
		phone.setName("form:phone");
		
		JLabel nameLabel = new JLabel("Імя:");
		nameLabel.setFont(font);
		
		JLabel surnameLabel = new JLabel("Прізвище:");
		surnameLabel.setFont(font);
		
		JLabel phoneLabel = new JLabel("Телефон:");
		phoneLabel.setFont(font);

		JLabel nameError = new JLabel();
		nameError.setFont(font);
		nameError.setName("error:name");
		nameError.setForeground(Color.RED);

		JLabel surnameError = new JLabel();
		surnameError.setFont(font);
		surnameError.setName("error:surname");
		surnameError.setForeground(Color.RED);

		JLabel phoneError = new JLabel();
		phoneError.setFont(font);
		phoneError.setForeground(Color.RED);
		phoneError.setName("error:phone");

		JPanel buttonPanel = new JPanel();
		
		JButton button = new JButton("Додати");
		button.setFont(font);
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					patientService.processAdding(jFrame);
				} catch (NotFoundException notFoundException) {
					notFoundException.printStackTrace();
				}
			}
		});
		
		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(nameError, BorderLayout.EAST);
		
		jFrame.add(namePanel);
		jFrame.add(name);
		
		JPanel surnamePanel = new JPanel();
		
		surnamePanel.setLayout(new BorderLayout());
		surnamePanel.add(surnameLabel, BorderLayout.WEST);
		surnamePanel.add(surnameError, BorderLayout.EAST);
		
		jFrame.add(surnamePanel);
		jFrame.add(surname);
		
		JPanel phonePanel = new JPanel();
		
		phonePanel.setLayout(new BorderLayout());
		phonePanel.add(phoneLabel, BorderLayout.WEST);
		phonePanel.add(phoneError, BorderLayout.EAST);
		
		jFrame.add(phonePanel);
		jFrame.add(phone);
		
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		jFrame.add(new JLabel());
		
		buttonPanel.add(button);
		jFrame.add(buttonPanel);
	}

}
