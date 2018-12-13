package by.epam.training.web.service.validator;

public class UserValidator {

	public static final int LOGIN_MIN_LENGTH = 4;
	public static final int LOGIN_MAX_LENGTH = 15;

	public static final int PASSWORD_MIN_LENGTH = 5;
	public static final int PASSWORD_MAX_LENGTH = 15;

	public ValidatorResult userSignInDataValidator(String userLogin, String userPassword) {
		ValidatorResult result = userLoginValidator(userLogin);
		if (!result.isValid()) {
			return result;
		}
		result = userPasswordValidator(userPassword);
		return result;
	}

	public ValidatorResult userSignUpDataValidator(String userLogin, String userPassword,
			String userConfirmedPassword) {
		ValidatorResult result = userLoginValidator(userLogin);
		if (!result.isValid()) {
			return result;
		}
		result = userPasswordValidator(userPassword, userConfirmedPassword);
		return result;
	}

	private ValidatorResult userLoginValidator(String userLogin) {
		if (userLogin == null || userLogin.isEmpty()) {
			return new ValidatorResult(false, ValidatorMessage.loginEmptyMessage);
		}
		if (userLogin.length() < LOGIN_MIN_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.loginTooShortMessage);
		}
		if (userLogin.length() > LOGIN_MAX_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.loginTooLongMessage);
		}
		return new ValidatorResult(true, ValidatorMessage.loginSatisfactoryMessage);
	}

	private ValidatorResult userPasswordValidator(String userPassword) {
		if (userPassword == null || userPassword.isEmpty()) {
			return new ValidatorResult(false, ValidatorMessage.passwordEmptyMessage);
		}
		if (userPassword.length() < PASSWORD_MIN_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.passwordTooShortMessage);
		}
		if (userPassword.length() > PASSWORD_MAX_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.passwordTooLongMessage);
		}
		return new ValidatorResult(true, ValidatorMessage.passwordSatisfactoryMessage);
	}

	private ValidatorResult userPasswordValidator(String userPassword, String userConfirmedPassword) {
		ValidatorResult result = userPasswordValidator(userPassword);
		if (!result.isValid()) {
			return result;
		}
		if (userConfirmedPassword == null || userConfirmedPassword.isEmpty()) {
			return new ValidatorResult(false, ValidatorMessage.passwordComfirmationEmptyMessage);
		}
		if (userPassword.compareTo(userConfirmedPassword) != 0) {
			return new ValidatorResult(false, ValidatorMessage.passwordComfirmationNotEqualsMessage);
		}
		return new ValidatorResult(true,
				result.getValidationMessage() + ValidatorMessage.passwordComfirmationSatisfactoryMessage);
	}

}
