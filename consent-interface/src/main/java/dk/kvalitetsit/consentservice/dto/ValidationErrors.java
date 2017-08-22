package dk.kvalitetsit.consentservice.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrors {

	private String message;
	
	private List<String> errors = new ArrayList<String>();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}
	
	public void addError(String error) {
		errors.add(error);
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}