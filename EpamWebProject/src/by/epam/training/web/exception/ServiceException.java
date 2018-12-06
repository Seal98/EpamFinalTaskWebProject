package by.epam.training.web.exception;

public class ServiceException extends Exception {
	
	private static final long serialVersionUID = -5256674132693364544L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
}
