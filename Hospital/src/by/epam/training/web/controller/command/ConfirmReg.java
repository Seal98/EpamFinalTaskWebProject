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
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		String confirmedPassword = request.getParameter(Command.passwordConfirmParameter);
		RequestDispatcher rd = null;
		String serviceException = null;
		try {
		clientService.signUp(login, password, confirmedPassword);
		rd = request.getRequestDispatcher(Command.mainPageJSP);
		request.setAttribute(Command.answerAttribute, Command.registrationConfirmedMessage);
		} catch(ServiceException se) {
			serviceException = se.getMessage();
			rd = request.getRequestDispatcher(Command.signUpPageJSP);
			request.setAttribute(Command.answerAttribute, serviceException);
			logger.info(se.getMessage());
		}
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

}
