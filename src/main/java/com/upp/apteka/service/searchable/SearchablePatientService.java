package com.upp.apteka.service.searchable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Patient;
import com.upp.apteka.component.combobox.searchable.SearchableItem;
import com.upp.apteka.component.combobox.searchable.SearchableService;
import com.upp.apteka.service.PatientService;
import com.upp.apteka.service.converter.SearchablePatient;

@Service
public class SearchablePatientService implements SearchableService {

	@Autowired
	private PatientService patientService;

	@Autowired
	private SearchablePatient searchablePatient;

	// @Override
	public List<SearchableItem> getSearchableItems(String query) {
		List<Patient> patients = patientService.findByQuery(query, true);

		List<SearchableItem> items = new ArrayList<SearchableItem>();

		for (Patient patient : patients)
			items.add(searchablePatient.convert(patient));

		return items;
	}

}
