package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class SignIn implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		RequestDispatcher rd = null;
		String serviceException = "User not found";
		try {
		clientService.signIn(login, password);
		rd = request.getRequestDispatcher("jsp/welcome.jsp");
		} catch(ServiceException se) {
			serviceException = se.getMessage();
			rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("answer", serviceException);
			System.out.println(se);
		}
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
