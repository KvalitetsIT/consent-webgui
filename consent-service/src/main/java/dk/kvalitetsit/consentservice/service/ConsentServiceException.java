package dk.kvalitetsit.consentservice.service;

import java.util.LinkedList;
import java.util.List;

public class ConsentServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	List<String> errors = new LinkedList<String>();
	
	public ConsentServiceException(String cause) {
		this.errors.add(cause);
	}

	public String addError(String error) {
		this.errors.add(error);
		return error;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
