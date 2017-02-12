package com.upp.apteka.validator;

import java.util.List;

public interface Validator {
	
	List<ValidationError> validate(Object target);

}
