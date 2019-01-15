package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ServiceFactory;

public class CancelAppointment implements Command {

	private static Logger logger = LogManager.getLogger(CancelAppointment.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String appointmentId = request.getParameter(Command.appointmentIdParameter);
		try {
			ServiceFactory.getInstance().getClientService().cancelAppointment(appointmentId);
			response.getWriter().print(Command.successMessage);
		} catch (ServiceException e) {
			logger.error(e);
		}
	}

}
