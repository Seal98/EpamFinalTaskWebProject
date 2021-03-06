package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class CompleteAppointment implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ServiceException {
		ClientService service = ServiceFactory.getInstance().getClientService();
		service.completeAppointment(request.getParameter(Command.appointmentIdParameter));
		response.getWriter().print(Command.successMessage);
	}

}
