package by.epam.training.web.controller.command.authorizer;

import java.util.HashMap;
import java.util.Map;

public class UserAuthorizerProvider {

	private final Map<UserType, UserAuthorizer> authorizers = new HashMap<>();
	
	private UserAuthorizerProvider() {
		authorizers.put(UserType.PATIENT, new PatientAuthorizer());
		authorizers.put(UserType.DOCTOR, new DoctorAuthorizer());
		authorizers.put(UserType.NURSE, new NurseAuthorizer());
	}
	
	public UserAuthorizer getAuthorizer(String type) {
		UserType userType;
		userType = UserType.valueOf(type.toUpperCase());
		return authorizers.get(userType);
		
	}
	
	private static class SingletonHandler{
		private static final UserAuthorizerProvider instance = new UserAuthorizerProvider();
	}
	
	public static UserAuthorizerProvider getInstance() {
		return SingletonHandler.instance;
	}
	
}
