package by.epam.training.web.service.validator;

public class AppointmentValidator {

	public ValidatorResult validate(String patientId, String executorId, String appointmentType) {
		if(patientId == null || !patientId.matches(ValidatorHelper.appointmentMark + ValidatorHelper.numberRegex)) {
			return new ValidatorResult(false, ValidatorMessage.patientIdNotValid);
		}
		if(executorId == null || !executorId.matches(ValidatorHelper.numberRegex)) {
			return new ValidatorResult(false, ValidatorMessage.executorIdNotValid);
		}
		if(appointmentType == null || !appointmentType.matches(ValidatorHelper.stringRegex + ValidatorHelper.buttonRbMark)) {
			return new ValidatorResult(false, ValidatorMessage.appointmentTypeNotValid);
		}
		return new ValidatorResult(true, ValidatorMessage.correctMessage);
	}
	
	public ValidatorResult validateId(String appointmentId) {
		if(!appointmentId.matches(ValidatorHelper.numberRegex)) {
			return new ValidatorResult(false, ValidatorMessage.appointmentIdNotValid);
		}
		return new ValidatorResult(true, ValidatorMessage.correctMessage);
	}
	
}
