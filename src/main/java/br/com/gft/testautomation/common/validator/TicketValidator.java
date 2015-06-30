package br.com.gft.testautomation.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.gft.testautomation.common.model.Release;

public class TicketValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return Release.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/* Validate each field using the Errors object, the field path and the message 
		 * that will be displayed if the field is empty or with white spaces. */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jira", "required.Jira", "Jira value is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "required.Description", "Description is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "developer", "required.Developer", "Developer name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tester", "required.Tester", "Tester name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "required.Status", "Status is required.");
	}
}
