package by.epam.training.web.service.validator;

public class AppointmentValidator {

	public ValidatorResult validate(String patientId, String executorId, String appointmentType) {
		if(patientId == null || !patientId.matches("appointment[0-9]+")) {
			return new ValidatorResult(false, ValidatorMessage.patientIdNotValid);
		}
		if(executorId == null || !executorId.matches("[0-9]+")) {
			return new ValidatorResult(false, ValidatorMessage.executorIdNotValid);
		}
		if(appointmentType == null || !appointmentType.matches("[a-zA-Z]+Rb")) {
			return new ValidatorResult(false, ValidatorMessage.appointmentTypeNotValid);
		}
		return new ValidatorResult(true, "Correct");
	}
	
	public ValidatorResult validateId(String appointmentId) {
		if(!appointmentId.matches("[0-9]+")) {
			return new ValidatorResult(false, ValidatorMessage.appointmentIdNotValid);
		}
		return new ValidatorResult(true, "Correct");
	}
	
}
