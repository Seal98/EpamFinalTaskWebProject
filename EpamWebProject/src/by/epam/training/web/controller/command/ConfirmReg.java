package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class ConfirmReg implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter("login");
		System.out.println(login);
		String password = request.getParameter("password");
		System.out.println(password);
		String confirmedPassword = request.getParameter("passwordConfirm");
		System.out.println(confirmedPassword);
		RequestDispatcher rd = null;
		String serviceException = null;
		try {
		clientService.signUp(login, password, confirmedPassword);
		rd = request.getRequestDispatcher("index.jsp");
		request.setAttribute("answer", "You have been registered");
		} catch(ServiceException se) {
			serviceException = se.getMessage();
			rd = request.getRequestDispatcher("jsp/signUp.jsp");
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
