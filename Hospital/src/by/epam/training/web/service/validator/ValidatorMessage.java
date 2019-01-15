package by.epam.training.web.service.validator;

public interface ValidatorMessage {

	String loginEmptyMessage = "Login is empty";
	String loginTooShortMessage = "Login is too short";
	String loginTooLongMessage = "Login is too long";
	String loginSatisfactoryMessage = "Login is satisfactory";

	String birthdateMessage = "Birthdate wasn't chosen";
	String therapistMessage = "Therapist wasn't chosen";
	String invalidIdMessage = "User id is not valid";
	String invalidDiagnosisMessage = "User diagnosis is not valid";
	String invalidFinalDiagnosisMessage = "User final diagnosis is not valid";
	String correctMessage = "Correct";
	
	String passwordEmptyMessage = "Password is empty";
	String passwordTooShortMessage = "Password is too short";
	String passwordTooLongMessage = "Password is too long";
	String passwordSatisfactoryMessage = "Password is satisfactory";

	String firstNameEmptyMessage = "First name is empty";
	String firstNameTooShortMessage = "First name is too short";
	String firstNameTooLongMessage = "First name is too long";
	String firstNameSatisfactoryMessage = "First name is satisfactory";

	String lastNameEmptyMessage = "Last name is empty";
	String lastNameTooShortMessage = "Last name is too short";
	String lastNameTooLongMessage = "Last name is too long";
	String lastNameSatisfactoryMessage = "Last name is satisfactory";	
	
	String passwordComfirmationEmptyMessage = "Password confirmation is empty";
	String passwordComfirmationNotEqualsMessage = "Password confirmation is not equals to the original password";
	String passwordComfirmationSatisfactoryMessage = " Password confirmation is satisfactory";
	
	String patientIdNotValid = "Something wrong with a patient info. Try again later";
	String executorIdNotValid = "Something wrong with an executor info. Try again later";
	String appointmentTypeNotValid = "Something wrong with an appointment-type info. Try again later";
	String appointmentIdNotValid = "Something wrong with an appointment id. Try again later";
	
	String invalidLanguage = "Invalid language";
}
