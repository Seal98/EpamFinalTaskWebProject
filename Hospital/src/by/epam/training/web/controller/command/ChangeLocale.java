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

public class ChangeLocale implements Command {

	private static Logger logger = LogManager.getLogger(ChangeLocale.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String locale = request.getParameter(Command.requestParameter);
		request.getSession(true).setAttribute(Command.localeParameter, locale);
		Object localeAttribute = request.getSession(true).getAttribute(Command.currentPageParameter);
		String currentPage = localeAttribute != null ? localeAttribute.toString() : Command.mainPageJSP;
		if (currentPage.compareTo(Command.mainPageJSP) == 0) {
			response.sendRedirect(Command.mainPageJSP);
		} else if (currentPage.compareTo(Command.signUpPageJSP) == 0) {
			response.sendRedirect(Command.signUpPageJSP);
		} else {
			request.getSession(true).setAttribute(Command.changeLocaleParameter, Command.signInParameter);
			try {
				ServiceFactory.getInstance().getClientService().changeUserLocale(locale,
						request.getSession(true).getAttribute(Command.idParameter).toString());
			} catch (ServiceException e) {
				logger.error(e);
			}
			request.getRequestDispatcher(Command.authorizationParameter).forward(request, response);
		}

	}
}
