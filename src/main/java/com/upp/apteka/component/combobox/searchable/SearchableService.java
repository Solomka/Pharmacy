package com.upp.apteka.component.combobox.searchable;

import java.util.List;

public interface SearchableService {
	List<SearchableItem> getSearchableItems(String query);
}
