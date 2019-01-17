package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class ConfirmReg implements Command {

	private static Logger logger = LogManager.getLogger(ConfirmReg.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		request.setAttribute(Command.answerAttribute, null);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();

		String firstName = request.getParameter(Command.firstNameParameter);
		String lastName = request.getParameter(Command.lastNameParameter);
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		String confirmedPassword = request.getParameter(Command.passwordConfirmParameter);
		String therapistId = request.getParameter(Command.therapistParameter);
		try {
			String birthdate = request.getParameter(Command.birthDateParameter);
			clientService.signUp(login, password, confirmedPassword, firstName, lastName, birthdate, therapistId);
			response.sendRedirect(Command.mainPageJSP);
			request.getSession(true).setAttribute(Command.currentPageParameter, Command.mainPageJSP);
		} catch (ServiceException se) {
			dispatcher = request.getRequestDispatcher(Command.signUpPageJSP);
			request.setAttribute(Command.regMistakeMessageParameter, se.getMessage());
			dispatcher.forward(request, response);
			logger.error(se);
		}
		
	}

}
