package by.epam.training.web.controller.command.authorizer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.bean.User;
import by.epam.training.web.controller.command.Command;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class NurseAuthorizer implements UserAuthorizer {

	@Override
	public void signIn(HttpServletRequest request, HttpServletResponse response, User existingUser) throws ServletException, IOException, ServiceException {
		ClientService clientService = ServiceFactory.getInstance().getClientService();
		request.setAttribute(Command.appointments, clientService.getExecutorAppointments(existingUser.getUserId(), existingUser.getUserType()));
		request.getRequestDispatcher(Command.executorPageJSP).forward(request, response);
		request.getSession(true).setAttribute(Command.currentPageParameter, Command.executorPageJSP);
	}

}
