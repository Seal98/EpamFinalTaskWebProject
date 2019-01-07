package by.epam.training.web.controller.command;

import java.io.IOException;

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
		request.getSession(true).setAttribute(Command.answerAttribute, null);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();

		String firstName = request.getParameter(Command.firstNameParameter);
		String lastName = request.getParameter(Command.lastNameParameter);
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		String confirmedPassword = request.getParameter(Command.passwordConfirmParameter);
		String therapistId = request.getParameter(Command.therapistParameter);
		System.out.println(therapistId);
		try {
			String birthdate = request.getParameter(Command.birthDateParameter);
			clientService.signUp(login, password, confirmedPassword, firstName, lastName, birthdate, therapistId);
			//dispatcher = request.getRequestDispatcher(Command.mainPageJSP);
			request.getSession(true).setAttribute(Command.answerAttribute, Command.registrationConfirmedMessage);
			response.sendRedirect(Command.mainPageJSP);
		} catch (ServiceException se) {
			dispatcher = request.getRequestDispatcher(Command.signUpPageJSP);
			request.getSession(true).setAttribute(Command.answerAttribute, se.getMessage());
			dispatcher.forward(request, response);
			throw se;
		}
		
	}

}
