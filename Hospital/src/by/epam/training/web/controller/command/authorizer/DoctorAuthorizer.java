package by.epam.training.web.controller.command.authorizer;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.bean.Appointment;
import by.epam.training.web.bean.Doctor;
import by.epam.training.web.bean.MedicalTreatment;
import by.epam.training.web.bean.User;
import by.epam.training.web.controller.command.Command;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class DoctorAuthorizer implements UserAuthorizer {

	@Override
	public void signIn(HttpServletRequest request, HttpServletResponse response, User existingUser) throws ServiceException, ServletException, IOException {
		ClientService clientService = ServiceFactory.getInstance().getClientService();
		if(((Doctor)existingUser).getSpecialization().toUpperCase().compareTo(Command.therapistUpperCase) == 0) {
			request.setAttribute(Command.attendedPatients, clientService.getAttendedPatients(existingUser.getUserId()));
			request.setAttribute(Command.executors, clientService.getExecutors());
			List<MedicalTreatment> treatment = clientService.getTreatment();
			request.setAttribute(Command.medicine, clientService.getMedicine(treatment));
			request.setAttribute(Command.procedures, clientService.getProcedures(treatment));
			request.setAttribute(Command.surgeries, clientService.getSurgeries(treatment));
			request.setAttribute(Command.appointments, clientService.getMadeAppointments(existingUser.getUserId()));
			request.getRequestDispatcher(Command.therapistPageJSP).forward(request, response);
			request.getSession(true).setAttribute(Command.currentPageParameter, Command.therapistPageJSP);
		} else {
			List<Appointment> appointments = clientService.getExecutorAppointments(existingUser.getUserId(), existingUser.getUserType());
			request.setAttribute(Command.appointments, appointments);
			request.getRequestDispatcher(Command.executorPageJSP).forward(request, response);
			request.getSession(true).setAttribute(Command.currentPageParameter, Command.executorPageJSP);
		}
	}

}
