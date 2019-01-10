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
		request.setAttribute("locale", request.getParameter("requestParameter"));
		RequestDispatcher rd = null;
		String uri = request.getRequestURI().split("/EpamWebProject/")[1];
		switch(uri) {
		case "authorization": rd = request.getRequestDispatcher(""); break;
		case "createUser": rd = request.getRequestDispatcher("WEB-INF/signUp.jsp"); break;
		default: rd = request.getRequestDispatcher(""); break;
		}
		
		rd.forward(request, response);
	}
}
