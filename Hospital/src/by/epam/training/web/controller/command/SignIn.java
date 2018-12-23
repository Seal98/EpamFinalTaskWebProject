package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.bean.User;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class SignIn implements Command {

    private static Logger logger = LogManager.getLogger(SignIn.class);
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getSession(true).setAttribute(Command.answerAttribute, null);
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		RequestDispatcher rd = null;
		try {
		User existingUser = clientService.signIn(login, password);
		request.setAttribute(Command.loginParameter, existingUser.getUserLogin());
		rd = request.getRequestDispatcher(Command.welcomePageJSP);
		rd.forward(request, response);
		} catch(ServiceException se) {
			request.getSession(true).setAttribute(Command.answerAttribute, se.getMessage());
			logger.info(se.getMessage());
			response.sendRedirect(Command.mainPageJSP);
		}
	}

}
