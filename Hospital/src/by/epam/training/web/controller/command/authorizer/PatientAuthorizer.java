package by.epam.training.web.controller.command.authorizer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.bean.Doctor;
import by.epam.training.web.bean.PatientCuringInfo;
import by.epam.training.web.bean.User;
import by.epam.training.web.controller.command.Command;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class PatientAuthorizer implements UserAuthorizer {

	@Override
	public void signIn(HttpServletRequest request, HttpServletResponse response, User existingUser) throws ServletException, IOException, ServiceException {
		ClientService clientService = ServiceFactory.getInstance().getClientService();
		PatientCuringInfo curingInfo = clientService.getUserInfo(existingUser.getUserId());
		request.setAttribute(Command.appointments, curingInfo.getAppointments());
		setAttendedDoctorInfo(request, curingInfo.getAttendedDoctor());
		request.getRequestDispatcher(Command.patientPageJSP).forward(request, response);
		request.getSession(true).setAttribute(Command.currentPageParameter, Command.patientPageJSP);
	}
	
	private void setAttendedDoctorInfo(HttpServletRequest request, User doctor) {
		request.setAttribute(Command.attendedDoctorFName, doctor.getFirstName());
		request.setAttribute(Command.attendedDoctorLName, doctor.getLastName());
		request.setAttribute(Command.attendedDoctorSpecialization, ((Doctor) doctor).getSpecialization());
	}
	
}
