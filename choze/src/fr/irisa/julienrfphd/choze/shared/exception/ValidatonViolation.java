package fr.irisa.julienrfphd.choze.shared.exception;

import java.util.List;

public class ValidatonViolation extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String[]> errors;

	public List<String[]> getErrors() {
		return errors;
	}

	public void setErrors(List<String[]> errors) {
		this.errors = errors;
	}

	public ValidatonViolation() {
		
	}
	
	public ValidatonViolation(List<String[]> errors) {
		super();
		this.errors = errors;
	}
}
