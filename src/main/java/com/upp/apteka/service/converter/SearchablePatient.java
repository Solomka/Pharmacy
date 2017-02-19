package com.upp.apteka.service.converter;

import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.component.combobox.searchable.SearchableItem;

@Service
public class SearchablePatient {
	public SearchableItem convert(Patient patient) {
		SearchableItem item = new SearchableItem(patient.getId(), patient.getSurname() + " " + patient.getName());
		return item;
	}
}
