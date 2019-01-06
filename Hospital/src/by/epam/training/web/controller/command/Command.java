package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;

public interface Command {
	
	public static final String mainPageJSP = "index.jsp";
	public static final String welcomePageJSP = "welcome";
	public static final String signUpPageJSP = "signUp";
	public static final String answerAttribute = "answer";
	public static final String loginParameter = "login";
	public static final String idParameter = "id";
	public static final String passwordParameter = "password";
	public static final String firstNameParameter = "fname";
	public static final String lastNameParameter = "lname";
	public static final String birthDateParameter = "bdate";
	public static final String passwordConfirmParameter = "passwordConfirm";
	public static final String registrationConfirmedMessage = "You have been registered";
	public static final String wrongCommandMessage = "Wrong command";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException;
}
