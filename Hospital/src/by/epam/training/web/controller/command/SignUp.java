package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ServiceFactory;

public class SignUp implements Command {

	private static Logger logger = LogManager.getLogger(SignUp.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(Command.signUpPageJSP);
		request.getSession(true).setAttribute(Command.answerAttribute, null);
		try {
			request.getSession(true).setAttribute("local", request.getAttribute("local"));
			request.getSession(true).setAttribute("therapists", ServiceFactory.getInstance().getClientService().getTherapists());
			rd.forward(request, response);
		} catch (ServletException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.info(e);
		} catch (ServiceException e) {
			logger.info(e);
		}
	}
}
