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

public class SignIn implements Command {

    private static Logger logger = LogManager.getLogger(SignIn.class);
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		RequestDispatcher rd = null;
		try {
		clientService.signIn(login, password);
		rd = request.getRequestDispatcher(Command.welcomePageJSP);
		} catch(ServiceException se) {
			rd = request.getRequestDispatcher(Command.mainPageJSP);
			request.setAttribute(Command.answerAttribute, se.getMessage());
			logger.info(se.getMessage());
		}
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

}
