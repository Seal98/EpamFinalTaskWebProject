package by.epam.training.web.controller.command;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class ConfirmReg implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServiceException, ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String serviceException = null;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();

		String firstName = request.getParameter(Command.firstNameParameter);
		String lastName = request.getParameter(Command.lastNameParameter);
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		String confirmedPassword = request.getParameter(Command.passwordConfirmParameter);
		
		try {
			String birthdate = request.getParameter(Command.birthDateParameter);
			clientService.signUp(login, password, confirmedPassword, firstName, lastName, birthdate);
			dispatcher = request.getRequestDispatcher(Command.mainPageJSP);
			request.setAttribute(Command.answerAttribute, Command.registrationConfirmedMessage);
		} catch (ServiceException se) {
			dispatcher = request.getRequestDispatcher(Command.signUpPageJSP);
			request.setAttribute(Command.answerAttribute, se.getMessage());
			dispatcher.forward(request, response);
			throw se;
		}
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}

}
