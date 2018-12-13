package by.epam.training.web.service.validator;

public interface ValidatorMessage {

	String loginEmptyMessage = "Login is empty";
	String loginTooShortMessage = "Login is too short";
	String loginTooLongMessage = "Login is too long";
	String loginSatisfactoryMessage = "Login is satisfactory";

	String passwordEmptyMessage = "Password is empty";
	String passwordTooShortMessage = "Password is too short";
	String passwordTooLongMessage = "Password is too long";
	String passwordSatisfactoryMessage = "Password is satisfactory";

	String passwordComfirmationEmptyMessage = "Password confirmation is empty";
	String passwordComfirmationNotEqualsMessage = "Password confirmation is not equals to the original password";
	String passwordComfirmationSatisfactoryMessage = " Password confirmation is satisfactory";
}
