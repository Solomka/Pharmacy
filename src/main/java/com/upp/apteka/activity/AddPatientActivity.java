package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import com.upp.apteka.validator.ValidationError;

@Component
@Scope("prototype")
public class AddPatientActivity {
	
	private static final int INPUT_WIDTH = 500;
	private static final int INPUT_HEIGHT = 40;

	@Autowired
	private JFrame jFrame;

	@Autowired
	private PatientService patientService;

	public void showActivity() {
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
		jFrame.setContentPane(mainPanel);
		jFrame.getContentPane().setLayout(new FlowLayout());
		
		JPanel namePanel = new JPanel();
		namePanel.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		
		JTextField name = new JTextField();
		name.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		name.setName("form:name");

		JTextField surname = new JTextField();
		surname.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		surname.setName("form:surname");

		JTextField phone = new JTextField();
		phone.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		phone.setName("form:phone");
		
		JLabel nameLabel = new JLabel("Імя:");
		JLabel surnameLabel = new JLabel("Прізвище:");
		JLabel phoneLabel = new JLabel("Телефон:");

		JLabel nameError = new JLabel();
		nameError.setName("error:name");
		nameError.setForeground(Color.RED);

		JLabel surnameError = new JLabel();
		surnameError.setName("error:surname");
		surnameError.setForeground(Color.RED);

		JLabel phoneError = new JLabel();
		phoneError.setForeground(Color.RED);
		phoneError.setName("error:phone");

		JButton button = new JButton("Click");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					List<ValidationError> errorsList = patientService.processAdding(jFrame);

					if (errorsList.isEmpty()) {
						System.out.println("Ама хасла!11");
					}
				} catch (Exception e228) {
					e228.printStackTrace();
				}
			}
		});
		
		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(nameError, BorderLayout.EAST);
		
		jFrame.add(namePanel);
		jFrame.add(name);
		
		JPanel surnamePanel = new JPanel();
		surnamePanel.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		
		surnamePanel.setLayout(new BorderLayout());
		surnamePanel.add(surnameLabel, BorderLayout.WEST);
		surnamePanel.add(surnameError, BorderLayout.EAST);
		
		jFrame.add(surnamePanel);
		jFrame.add(surname);
		
		JPanel phonePanel = new JPanel();
		phonePanel.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		
		phonePanel.setLayout(new BorderLayout());
		phonePanel.add(phoneLabel, BorderLayout.WEST);
		phonePanel.add(phoneError, BorderLayout.EAST);
		
		jFrame.add(phonePanel);
		jFrame.add(phone);
		
		jFrame.add(button);
	}

}
