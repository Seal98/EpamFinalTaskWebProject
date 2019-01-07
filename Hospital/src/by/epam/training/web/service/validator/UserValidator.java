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
		ValidatorResult result = new ValidatorResult(true, ValidatorMessage.correctMessage);
		userLoginValidator(userLogin, result);
		if (!result.isValid()) {
			return result;
		}
		userPasswordValidator(userPassword, result);
		return result;
	}

	public ValidatorResult userSignUpDataValidator(String userLogin, String userPassword, String userConfirmedPassword,
			String userFirstName, String userLastName, String therapistId) {
		ValidatorResult result = new ValidatorResult(true, ValidatorMessage.correctMessage);
		userFirstNameValidator(userFirstName, result);
		if (!result.isValid()) {
			return result;
		}
		userLastNameValidator(userLastName, result);
		if (!result.isValid()) {
			return result;
		}
		userLoginValidator(userLogin, result);
		if (!result.isValid()) {
			return result;
		}
		userIdValidator(result, therapistId);
		if (!result.isValid()) {
			return result;
		}		
		userPasswordValidator(userPassword, userConfirmedPassword, result);
		return result;
	}

	private void userIdValidator(ValidatorResult result, String userId) {
		System.out.println(userId);
		if(userId == null || !userId.matches("[0-9]+")) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.invalidIdMessage);
		}
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
	
	private void userFirstNameValidator(String userFirstName, ValidatorResult result) {
		if (userFirstName == null || userFirstName.isEmpty()) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.firstNameEmptyMessage);
		}else if (userFirstName.length() < FIRST_NAME_MIN_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.firstNameTooShortMessage);
		}else if (userFirstName.length() > FIRST_NAME_MAX_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.firstNameTooLongMessage);
		}
	}

	private void userLastNameValidator(String userLastName, ValidatorResult result) {
		if (userLastName == null || userLastName.isEmpty()) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.lastNameEmptyMessage);
		}else if (userLastName.length() < LAST_NAME_MIN_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.lastNameTooShortMessage);
		}else if (userLastName.length() > LAST_NAME_MAX_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.lastNameTooLongMessage);
		}
	}

	private void userLoginValidator(String userLogin, ValidatorResult result) {
		if (userLogin == null || userLogin.isEmpty()) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.loginEmptyMessage);
		}else if (userLogin.length() < LOGIN_MIN_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.loginTooShortMessage);
		}else if (userLogin.length() > LOGIN_MAX_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.loginTooLongMessage);
		}
	}

	private void userPasswordValidator(String userPassword, ValidatorResult result) {
		if (userPassword == null || userPassword.isEmpty()) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.passwordEmptyMessage);
		}else if (userPassword.length() < PASSWORD_MIN_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.passwordTooShortMessage);
		}else if (userPassword.length() > PASSWORD_MAX_LENGTH) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.passwordTooLongMessage);
		}
	}

	private void userPasswordValidator(String userPassword, String userConfirmedPassword, ValidatorResult result) {
		userPasswordValidator(userPassword, result);
		if (!result.isValid()) {
			return;
		}else if (userConfirmedPassword == null || userConfirmedPassword.isEmpty()) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.passwordComfirmationEmptyMessage);
		}else if (userPassword.compareTo(userConfirmedPassword) != 0) {
			result.setValid(false);
			result.setValidationMessage(ValidatorMessage.passwordComfirmationNotEqualsMessage);
		}
	}

}
