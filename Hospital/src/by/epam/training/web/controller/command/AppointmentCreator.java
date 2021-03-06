package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class AppointmentCreator implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ServiceException {
			ClientService service = ServiceFactory.getInstance().getClientService();
			String patientId = request.getParameter(Command.patientIdParameter);
			String executorId = request.getParameter(Command.executorIdParameter);
			String selectedTreatmentType = request.getParameter(Command.treatmentTypeParameter);
			String selectedTreatment = request.getParameter(Command.treatmentParameter);
			String doctorId = request.getParameter(Command.doctorIdParameter).toString();
			int appointmentId = service.createAppointment(patientId, executorId, selectedTreatmentType, selectedTreatment, doctorId);
			if(appointmentId == -1) {
				response.getWriter().print(Command.creationFailedMessage);
			} else {
				response.getWriter().print(appointmentId);
			}
	}

}
