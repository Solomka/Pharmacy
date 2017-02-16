package com.upp.apteka.service.searchable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Medicine;
import com.upp.apteka.component.combobox.searchable.SearchableItem;
import com.upp.apteka.component.combobox.searchable.SearchableService;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.service.converter.SearchableMedicine;

@Service
public class SearchableMedicineService implements SearchableService {

	@Autowired
	private MedicineRepository medicineService;

	@Autowired
	private SearchableMedicine searchableMedicine;

	// @Override
	public List<SearchableItem> getSearchableItems(String query) {
		List<Medicine> medicines = medicineService.findByNameOrProducer(query);

		List<SearchableItem> items = new ArrayList<SearchableItem>();

		for (Medicine medicine : medicines)
			items.add(searchableMedicine.convert(medicine));

		return items;
	}

}
