package br.com.gft.testautomation.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.gft.testautomation.common.model.Release;

/** Class responsible for validating the required fields on the Release object.
 * Implements the Spring Validator interface */
public class ReleaseValidator implements Validator{
	
	/** Set which class will be validated */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class clazz) {		
		return Release.class.isAssignableFrom(clazz);
	}

	/** Method that validate, receiving a target object and a Errors object to compare and send
	 * back any necessary messages */
	public void validate(Object target, Errors errors) {
		
		/* Validate each field using the Errors object, the field path and the message 
		 * that will be displayed if the field is empty or with white spaces. */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "project", "required.Project", "Project name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tag", "required.Tag", "Tag is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.Name", "Release name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "target_date", "required.Target_Date", "Target date is required.");
	}	
}
