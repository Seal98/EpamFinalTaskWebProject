package by.epam.training.web.service.validator;

public class UserValidator {

	public static final int LOGIN_MIN_LENGTH = 4;
	public static final int LOGIN_MAX_LENGTH = 15;

	public static final int FIRST_NAME_MIN_LENGTH = 1;
	public static final int FIRST_NAME_MAX_LENGTH = 30;

	public static final int LAST_NAME_MIN_LENGTH = 1;
	public static final int LAST_NAME_MAX_LENGTH = 30;

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

	public ValidatorResult userSignUpDataValidator(String userLogin, String userPassword, String userConfirmedPassword,
			String userFirstName, String userLastName) {
		ValidatorResult result = userFirstNameValidator(userFirstName);
		if (!result.isValid()) {
			return result;
		}
		result = userLastNameValidator(userLastName);
		if (!result.isValid()) {
			return result;
		}
		result = userLoginValidator(userLogin);
		if (!result.isValid()) {
			return result;
		}
		result = userPasswordValidator(userPassword, userConfirmedPassword);
		return result;
	}

	public ValidatorResult validateDischargeInfo(String userId, String diagnosis, String finalDiagnosis) {
		if(userId == null || !userId.matches("[0-9]+")) {
			return new ValidatorResult(false, ValidatorMessage.invalidIdMessage);
		}
		if(diagnosis == null || diagnosis.isEmpty()) {
			return new ValidatorResult(false, ValidatorMessage.invalidDiagnosisMessage);
		}
		if(finalDiagnosis == null || finalDiagnosis.isEmpty()) {
			return new ValidatorResult(false, ValidatorMessage.invalidFinalDiagnosisMessage);
		}
		return new ValidatorResult(true, ValidatorMessage.correctMessage);
	}
	
	private ValidatorResult userFirstNameValidator(String userFirstName) {
		if (userFirstName == null || userFirstName.isEmpty()) {
			return new ValidatorResult(false, ValidatorMessage.firstNameEmptyMessage);
		}
		if (userFirstName.length() < FIRST_NAME_MIN_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.firstNameTooShortMessage);
		}
		if (userFirstName.length() > FIRST_NAME_MAX_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.firstNameTooLongMessage);
		}
		return new ValidatorResult(true, ValidatorMessage.firstNameSatisfactoryMessage);
	}

	private ValidatorResult userLastNameValidator(String userLastName) {
		if (userLastName == null || userLastName.isEmpty()) {
			return new ValidatorResult(false, ValidatorMessage.lastNameEmptyMessage);
		}
		if (userLastName.length() < LAST_NAME_MIN_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.lastNameTooShortMessage);
		}
		if (userLastName.length() > LAST_NAME_MAX_LENGTH) {
			return new ValidatorResult(false, ValidatorMessage.lastNameTooLongMessage);
		}
		return new ValidatorResult(true, ValidatorMessage.lastNameSatisfactoryMessage);
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
