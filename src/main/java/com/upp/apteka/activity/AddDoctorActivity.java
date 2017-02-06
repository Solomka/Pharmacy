package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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

import com.upp.apteka.service.DoctorService;
import com.upp.apteka.validator.ValidationError;

@Component
@Scope("prototype")
public class AddDoctorActivity {

	@Autowired
	private JFrame jFrame;

	@Autowired
	private DoctorService doctorService;

	public void showActivity() {
		
		GridLayout gridLayout = new GridLayout(0,1,0,5);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
		jFrame.setContentPane(mainPanel);
		jFrame.getContentPane().setLayout(gridLayout);
		
		JPanel namePanel = new JPanel();
		
		JTextField name = new JTextField();
		name.setName("form:name");

		JTextField surname = new JTextField();
		surname.setName("form:surname");

		JTextField occupation = new JTextField();
		occupation.setName("form:occupation");

		JTextField standing = new JTextField();
		standing.setName("form:standing");
		
		JLabel nameLabel = new JLabel("Імя:");
		JLabel surnameLabel = new JLabel("Прізвище:");
		JLabel occupationLabel = new JLabel("Спеціальність:");
		JLabel standingLabel = new JLabel("Стаж:");

		JLabel nameError = new JLabel();
		nameError.setName("error:name");
		nameError.setForeground(Color.RED);

		JLabel surnameError = new JLabel();
		surnameError.setName("error:surname");
		surnameError.setForeground(Color.RED);

		JLabel occupationError = new JLabel();
		occupationError.setForeground(Color.RED);
		occupationError.setName("error:occupation");
		
		JLabel standingError = new JLabel();
		standingError.setForeground(Color.RED);
		standingError.setName("error:standing");

		JButton button = new JButton("Click");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					List<ValidationError> errorsList = doctorService.processAdding(jFrame);

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
		
		surnamePanel.setLayout(new BorderLayout());
		surnamePanel.add(surnameLabel, BorderLayout.WEST);
		surnamePanel.add(surnameError, BorderLayout.EAST);
		
		jFrame.add(surnamePanel);
		jFrame.add(surname);
		
		JPanel occupationPanel = new JPanel();
		
		occupationPanel.setLayout(new BorderLayout());
		occupationPanel.add(occupationLabel, BorderLayout.WEST);
		occupationPanel.add(occupationError, BorderLayout.EAST);
		
		jFrame.add(occupationPanel);
		jFrame.add(occupation);
		
		JPanel standingPanel = new JPanel();
		
		standingPanel.setLayout(new BorderLayout());
		standingPanel.add(standingLabel, BorderLayout.WEST);
		standingPanel.add(standingError, BorderLayout.EAST);
		
		jFrame.add(standingPanel);
		jFrame.add(standing);
		
		jFrame.add(new JLabel(""));
		jFrame.add(button);
	}

}
