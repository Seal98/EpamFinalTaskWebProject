package by.epam.training.web.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.bean.Appointment;
import by.epam.training.web.bean.Doctor;
import by.epam.training.web.bean.MedicalTreatment;
import by.epam.training.web.bean.PatientCuringInfo;
import by.epam.training.web.bean.User;
import by.epam.training.web.controller.command.authorizer.UserAuthorizerProvider;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class SignIn implements Command {

	private static Logger logger = LogManager.getLogger(SignIn.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		if(login == null) {
			login = request.getSession(true).getAttribute(Command.loginParameter).toString();
			password = request.getSession(true).getAttribute(Command.passwordParameter).toString();
		}
		try {
			User existingUser = clientService.signIn(login, password);
			String userType = existingUser.getUserType();
			request.setAttribute(Command.userType, userType);
			setUserSessionParameters(request, existingUser);
			UserAuthorizerProvider.getInstance().getAuthorizer(userType).signIn(request, response, existingUser);
		} catch (ServiceException se) {
			request.setAttribute(Command.answerAttribute, se.getMessage());
			logger.error(se);
			request.getRequestDispatcher(Command.mainPageJSP).forward(request, response);
		}
	}

	private void setUserSessionParameters(HttpServletRequest request, User existingUser) {
		request.getSession(true).setAttribute(Command.currentUser, existingUser);
		request.getSession(true).setAttribute(Command.loginParameter, existingUser.getUserLogin());
		request.getSession(true).setAttribute(Command.passwordParameter, existingUser.getUserPassword());
		request.getSession(true).setAttribute(Command.idParameter, existingUser.getUserId());
		if(existingUser.getLanguage().compareTo(Command.notDefinedLanguage) != 0) {
			request.getSession(true).setAttribute(Command.localeParameter, existingUser.getLanguage());
		}
	}
	
}
