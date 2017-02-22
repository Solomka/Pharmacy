package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.DeliveryMedicine;
import com.upp.apteka.config.Mapper;

@Component("viewDeliveryMedicinesActivity")
@Scope("prototype")
public class ViewDeliveryMedicinesActivity implements Activity {

	@Autowired
	private Mapper mapper;

	@Autowired
	private JFrame jFrame;

	private JTable medicinesTable;

	private Object[] columnsHeader = new String[] { "Назва", "Виробник", "Ціна коробки", "К-сть в коробці",
			"К-сть коробок" };

	private JTextField queryField;

	private static final int BUTTON_HEIGHT = 20;
	private static final int BUTTON_WIDTH = 120;

	private static final int PAGINATION_BUTTON_HEIGHT = 25;
	private static final int PAGINATION_BUTTON_WIDTH = 90;

	private static final int ROW_HEIGHT = 32;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	private List<DeliveryMedicine> medicines;

	@SuppressWarnings("unchecked")
	// @Override
	public void showActivity(final Map<String, Object> params) {

		// add main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		jFrame.setContentPane(mainPanel);
		jFrame.setLayout(new BorderLayout());

		// get data from params
		medicines = (List<DeliveryMedicine>) params.get("medicines");

		/*
		 * work with table
		 */

		medicinesTable = new JTable(getData(medicines), columnsHeader);
		medicinesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		medicinesTable.setFont(font);
		medicinesTable.setRowHeight(ROW_HEIGHT);
		medicinesTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		medicinesTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JScrollPane scrollPane = new JScrollPane(medicinesTable);
		scrollPane.setBorder(BorderFactory.createLineBorder(jFrame.getBackground()));
		jFrame.add(scrollPane, BorderLayout.CENTER);
	}

	/*
	 * table fill in
	 */

	private Object[][] getData(List<DeliveryMedicine> medicines) {
		Object[][] array = new Object[medicines.size()][columnsHeader.length];

		for (int i = 0; i < medicines.size(); i++) {
			array[i][0] = medicines.get(i).getMedicine().getName();
			array[i][1] = medicines.get(i).getMedicine().getProducer();
			array[i][2] = medicines.get(i).getMedicine().getBoxPrice().toString();
			array[i][3] = medicines.get(i).getMedicine().getQuantityPerBox();
			array[i][4] = medicines.get(i).getBoxQuantity();
		}

		return array;

	}

}
