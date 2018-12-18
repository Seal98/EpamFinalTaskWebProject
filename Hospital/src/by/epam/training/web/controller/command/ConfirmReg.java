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
		String serviceException = null;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();

		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		String confirmedPassword = request.getParameter(Command.passwordConfirmParameter);

		try {
			clientService.signUp(login, password, confirmedPassword);
			dispatcher = request.getRequestDispatcher(Command.mainPageJSP);
			request.setAttribute(Command.answerAttribute, Command.registrationConfirmedMessage);
		} catch (ServiceException se) {
			serviceException = se.getMessage();
			dispatcher = request.getRequestDispatcher(Command.signUpPageJSP);
			request.setAttribute(Command.answerAttribute, serviceException);
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
