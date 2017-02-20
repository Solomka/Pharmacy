package com.upp.apteka.validator;

public class ValidationError {

	private String objectName;
	private String description;

	public ValidationError(String objectName, String description) {

		this.objectName = objectName;
		this.description = description;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ValidationError [objectName=" + objectName + ", description=" + description + "]";
	}
	
	

}
