package by.epam.training.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	
	public static final String mainPageJSP = "index.jsp";
	public static final String welcomePageJSP = "jsp/welcome.jsp";
	public static final String signUpPageJSP = "jsp/signUp.jsp";
	public static final String answerAttribute = "answer";
	public static final String loginParameter = "login";
	public static final String passwordParameter = "password";
	public static final String passwordConfirmParameter = "passwordConfirm";
	public static final String registrationConfirmedMessage = "You have been registered";
	public static final String wrongCommandMessage = "Wrong command";
	
	public void execute(HttpServletRequest request, HttpServletResponse response);
}
