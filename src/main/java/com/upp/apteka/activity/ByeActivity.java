package com.upp.apteka.activity;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.config.Mapper;

@Component
@Scope("prototype")
public class ByeActivity {

	@Autowired
	private Mapper mapper;

	@Autowired
	private JFrame jFrame;

	private String text;
	
	public ByeActivity(String text){
		this.text = text;
	}

	public void showActivity() {
		jFrame.setLayout(new GridLayout(1, 0));
		JLabel label = new JLabel(text);
		jFrame.add(label);
		JButton button = new JButton("Click");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapper.changeActivity("addPatient", null);
			}
		});
		jFrame.add(button);
	}

}
