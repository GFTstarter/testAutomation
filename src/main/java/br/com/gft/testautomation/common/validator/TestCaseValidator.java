package br.com.gft.testautomation.common.validator;

import junit.framework.TestCase;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TestCaseValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TestCase.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testcase_description", "required.Testcase_description", "Description is required.");
	}

}
