package com.upp.apteka.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.upp.apteka.bo.PharmacyMedicine;
import com.upp.apteka.config.Mapper;
import com.upp.apteka.service.MedicineService;

@Component("viewPharmaciesMedicineActivity")
@Scope("prototype")
public class ViewPharmaciesMedicineActivity implements Activity {

	@Autowired
	private Mapper mapper;

	@Autowired
	private JFrame jFrame;

	private JTable medicinesTable;

	private Object[] columnsHeader = new String[] { "Назва аптеки", "Назва", "Виробник", "Ціна за упаковку", "К-сть в аптеці" };

	private JTextField queryField;
	private JTextField queryPmField;

	private static final int BUTTON_HEIGHT = 20;
	private static final int BUTTON_WIDTH = 120;

	private static final int PAGINATION_BUTTON_HEIGHT = 25;
	private static final int PAGINATION_BUTTON_WIDTH = 90;

	private static final int ROW_HEIGHT = 32;

	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	private List<PharmacyMedicine> medicines;

	private int lastPage;
	private int currentPage;

	private String query;

	@Autowired
	private MedicineService medicineService;

	@SuppressWarnings("unchecked")
	// @Override
	public void showActivity(final Map<String, Object> params) {

		// add main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		jFrame.setContentPane(mainPanel);
		jFrame.setLayout(new BorderLayout());

		// get data from params
		medicines = (List<PharmacyMedicine>) params.get("medicines");
		lastPage = (Integer) params.get("last");

		currentPage = (Integer) params.get("current");
	
		/*
		 * search in pharmacy panel
		 */
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));		

		JPanel fieldWrapper = new JPanel();
		
		queryField = new JTextField();		

		JButton queryButton = new JButton("Пошук по мережі аптеці");
		queryButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));	
		
		queryField.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT - 5));
		
		/*
		 * queryButton listener
		 */
		queryButton.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				params.put("current", currentPage);
				params.put("query", queryField.getText());

				mapper.changeActivity("viewPharmaciesMedicine", params);
			}
		});		

		query = (String) params.get("query");
		queryField.setText(query);

		fieldWrapper.setLayout(new GridLayout(0, 1));
		fieldWrapper.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		fieldWrapper.add(queryField);		

		JPanel contPanel = new JPanel();
		contPanel.setLayout(new FlowLayout());		

		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(25, BUTTON_HEIGHT));
		searchPanel.add(emptyPanel, BorderLayout.WEST);	
				
		JLabel infoLabel = new JLabel("Сторінка: " + currentPage + "/" + lastPage);
		infoLabel.setFont(font);		
				
		contPanel.add(infoLabel);
		contPanel.add(emptyPanel);					

		searchPanel.add(contPanel, BorderLayout.WEST);
		searchPanel.add(fieldWrapper, BorderLayout.CENTER);
		searchPanel.add(queryButton, BorderLayout.EAST);
		
		jFrame.add(searchPanel, BorderLayout.NORTH);

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

		JPanel paginationPanel = new JPanel();
		paginationPanel.setLayout(new BorderLayout());

		JButton nextButton = new JButton("Далі");
		nextButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		/*
		 * next button listener
		 */
		nextButton.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				params.put("current", (Integer) (currentPage + 1));
				mapper.changeActivity("allPharmacyMedicines", params);
			}
		});

		if (currentPage == lastPage)
			nextButton.setEnabled(false);

		JButton prevButton = new JButton("Назад");
		prevButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		prevButton.addActionListener(new ActionListener() {

			/*
			 * prevButton listenter
			 */

			// @Override
			public void actionPerformed(ActionEvent e) {

				params.put("current", (Integer) (currentPage - 1));
				mapper.changeActivity("allPharmacyMedicines", params);
			}
		});

		if (currentPage == 1)
			prevButton.setEnabled(false);

		JPanel goPanel = new JPanel();
		final JTextField goTo = new JTextField(10);
		goPanel.setLayout(new GridLayout(0, 1));
		goPanel.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));
		goPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		goPanel.add(goTo);

		JButton goButton = new JButton("Перейти");
		goButton.setPreferredSize(new Dimension(PAGINATION_BUTTON_WIDTH, PAGINATION_BUTTON_HEIGHT));

		/*
		 * goButton listener
		 */
		goButton.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				try {
					int page = Integer.valueOf(goTo.getText());

					if (page > lastPage)
						page = lastPage;

					if (page < 0)
						page = 0;

					params.put("current", page);
					mapper.changeActivity("allPharmacyMedicines", params);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(jFrame, new String[] { "Числа нормальні треба вводити!" }, "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		paginationPanel.setLayout(new FlowLayout());
		paginationPanel.add(nextButton, BorderLayout.WEST);
		paginationPanel.add(goPanel, BorderLayout.WEST);
		paginationPanel.add(goButton, BorderLayout.WEST);
		paginationPanel.add(prevButton, BorderLayout.WEST);

		jFrame.add(paginationPanel, BorderLayout.SOUTH);
	}

	/*
	 * table fill in
	 */

	private Object[][] getData(List<PharmacyMedicine> medicines) {
		Object[][] array = new Object[medicines.size()][columnsHeader.length];

		for (int i = 0; i < medicines.size(); i++) {
			array[i][0] = medicines.get(i).getPharmacy().getName() + "(" + medicines.get(i).getPharmacy().getAddress() + ")";
			array[i][1] = medicines.get(i).getMedicine().getName();
			array[i][2] = medicines.get(i).getMedicine().getProducer();
			array[i][3] = medicines.get(i).getPackPrice().toString();
			array[i][4] = medicines.get(i).getPackQuantity();			
		}

		return array;

	}


}
