package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;

public class ChangeLocale implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ServiceException {
		System.out.println("Locale: " + request.getParameter("requestParameter"));
		RequestDispatcher dispatcher = null;
		request.getSession(true).setAttribute("locale", request.getParameter("requestParameter"));
		dispatcher = request.getRequestDispatcher(Command.mainPageJSP);
		dispatcher.forward(request, response);
	}
}
