package com.upp.apteka.component.combobox.searchable;

import java.awt.Color;
import java.awt.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

public class SearchableComboBox extends JComboBox<SearchableItem> {

	private static final long serialVersionUID = -6092483804328909718L;
	private static final int DELAY = 3000;

	private SearchableService searchableService;

	public SearchableComboBox(SearchableService searchableService) {

		this.searchableService = searchableService;
		this.setEditable(true);

		this.getEditor().getEditorComponent().addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(final KeyEvent e) {
				final String currentValue = ((JTextComponent) ((JComboBox<?>) ((Component) e.getSource()).getParent())
						.getEditor().getEditorComponent()).getText() + e.getKeyChar();

				new Thread() {

					@Override
					public void run() {

						if (currentValue.length() < 3) {
							SearchableComboBox.this.setBackground(Color.RED);
							return;
						} else
							SearchableComboBox.this.setBackground(null);

						try {
							Thread.sleep(DELAY);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						String newValue = ((JTextComponent) ((JComboBox<?>) ((Component) e.getSource()).getParent())
								.getEditor().getEditorComponent()).getText();

						if (currentValue.equals(newValue)) {
							SearchableComboBox.this.removeAllItems();

							List<SearchableItem> items = SearchableComboBox.this.searchableService
									.getSearchableItems(currentValue);

							for (SearchableItem item : items)
								SearchableComboBox.this.addItem(item);

							if (items.isEmpty()) {
								SearchableComboBox.this.setBackground(Color.RED);
								((JTextComponent) ((JComboBox<?>) ((Component) e.getSource()).getParent()).getEditor()
										.getEditorComponent()).setText(null);
							}
						}
					}
				}.start();

			}
		});
	}

}
