package by.epam.training.web.service.validator;

public class ValidatorHelper {
	
	private static final UserValidator userValidator = new UserValidator();

	public static UserValidator getUserValidator() {
		return userValidator;
	}
	
}
