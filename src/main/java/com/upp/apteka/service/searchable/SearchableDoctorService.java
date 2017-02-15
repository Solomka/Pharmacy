package com.upp.apteka.service.searchable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.component.combobox.searchable.SearchableItem;
import com.upp.apteka.component.combobox.searchable.SearchableService;
import com.upp.apteka.service.DoctorService;
import com.upp.apteka.service.converter.SearchableDoctor;

@Service
public class SearchableDoctorService implements SearchableService{
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private SearchableDoctor searchableDoctor;

	@Override
	public List<SearchableItem> getSearchableItems(String query) {
		List<Doctor> doctors = doctorService.findByQuery(query, true);
		
		List<SearchableItem> items = new ArrayList<>();
		
		for(Doctor doctor: doctors)
			items.add(searchableDoctor.convert(doctor));
		
		return items;
	}

}
