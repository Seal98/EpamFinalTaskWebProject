package by.epam.training.web.service.validator;

public class ValidatorResult {

	private boolean isValid = false;
	private String validationMessage;
	
	public ValidatorResult(boolean isValid, String validationMessage) {
		this.isValid = isValid;
		this.validationMessage = validationMessage;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public String getValidationMessage() {
		return validationMessage;
	}
	
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	
}
