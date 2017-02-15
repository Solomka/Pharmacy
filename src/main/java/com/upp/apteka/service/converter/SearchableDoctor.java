package com.upp.apteka.service.converter;

import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.component.combobox.searchable.SearchableItem;

@Service
public class SearchableDoctor {
	public SearchableItem convert(Doctor doctor) {
		SearchableItem item = new SearchableItem(doctor.getId(), doctor.getSurname() + " " + doctor.getName());
		return item;
	}
}
