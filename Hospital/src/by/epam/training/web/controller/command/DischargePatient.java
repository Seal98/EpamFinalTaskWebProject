package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class DischargePatient implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ServiceException {
		String userId = request.getParameter("userId");
		String diagnosis = request.getParameter("diagnosis");
		String finalDiagnosis = request.getParameter("finalDiagnosis");		
		ClientService service = ServiceFactory.getInstance().getClientService();
		service.dischargePatient(userId, diagnosis, finalDiagnosis);
		response.getWriter().print("success");
	}

}
