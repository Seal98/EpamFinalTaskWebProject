package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ServiceFactory;

public class CancelAppointment implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ServiceException {
		String appointmentId = request.getParameter("appointmentId");
		ServiceFactory.getInstance().getClientService().cancelAppointment(appointmentId);
		response.getWriter().print("success");
	}

}
