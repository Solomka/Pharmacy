package com.upp.apteka.service.converter;

import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.component.combobox.searchable.SearchableItem;

@Service
public class SearchableMedicine {
	public SearchableItem convert(Medicine medicine) {
		SearchableItem item = new SearchableItem(medicine.getId(), medicine.getName() + " " + medicine.getProducer());
		return item;
	}
}
