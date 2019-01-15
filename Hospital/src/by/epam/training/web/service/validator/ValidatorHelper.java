package by.epam.training.web.service.validator;

public class ValidatorHelper {
	
	public static final String numberRegex = "[0-9]+";
	public static final String stringRegex = "[a-zA-Z]+";
	public static final String appointmentMark = "appointment";
	public static final String buttonRbMark = "Rb";
	
	private static final UserValidator userValidator = new UserValidator();
	private static final AppointmentValidator appointmentValidator = new AppointmentValidator();

	public static UserValidator getUserValidator() {
		return userValidator;
	}

	public static AppointmentValidator getAppointmentValidator() {
		return appointmentValidator;
	}	
	
}
