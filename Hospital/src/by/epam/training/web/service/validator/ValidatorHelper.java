package by.epam.training.web.service.validator;

public class ValidatorHelper {
	
	private static final UserValidator userValidator = new UserValidator();
	private static final AppointmentValidator appointmentValidator = new AppointmentValidator();

	public static UserValidator getUserValidator() {
		return userValidator;
	}

	public static AppointmentValidator getAppointmentValidator() {
		return appointmentValidator;
	}	
	
}
