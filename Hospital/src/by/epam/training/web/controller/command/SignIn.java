package by.epam.training.web.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.bean.Appointment;
import by.epam.training.web.bean.Doctor;
import by.epam.training.web.bean.MedicalTreatment;
import by.epam.training.web.bean.PatientCuringInfo;
import by.epam.training.web.bean.User;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.ClientService;
import by.epam.training.web.service.ServiceFactory;

public class SignIn implements Command {

	private static Logger logger = LogManager.getLogger(SignIn.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getSession(true).setAttribute(Command.answerAttribute, null);
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		RequestDispatcher rd = null;
		try {
			User existingUser = clientService.signIn(login, password);

			String userType = existingUser.getClass().getSimpleName();
			request.getSession(true).setAttribute("userType", userType);
			request.getSession(true).setAttribute(Command.loginParameter, existingUser.getUserLogin());
			request.getSession(true).setAttribute(Command.idParameter, existingUser.getUserId());

			if (userType.toUpperCase().compareTo("PATIENT") == 0) {
				PatientCuringInfo curingInfo = clientService.getUserInfo(existingUser.getUserId());
				request.getSession(true).setAttribute("appointments", curingInfo.getAppointments());
				linkAttendedDoctorInfo(request, curingInfo.getAttendedDoctor());
				rd = request.getRequestDispatcher("patientPage");
			} else if (userType.toUpperCase().compareTo("DOCTOR") == 0) {
				if(((Doctor)existingUser).getSpecialization().toUpperCase().compareTo("THERAPIST") == 0) {
					request.getSession(true).setAttribute("attended_patients", clientService.getAttendedPatients(existingUser.getUserId()));
					request.getSession(true).setAttribute("executors", clientService.getExecutors());
					List<MedicalTreatment> treatment = clientService.getTreatment();
					request.getSession(true).setAttribute("medicine", clientService.getMedicine(treatment));
					request.getSession(true).setAttribute("procedures", clientService.getProcedures(treatment));
					request.getSession(true).setAttribute("surgeries", clientService.getSurgeries(treatment));
					request.getSession(true).setAttribute("appointments", clientService.getMadeAppointments(existingUser.getUserId()));
					rd = request.getRequestDispatcher("therapistPage");
				} else {
					List<Appointment> appointments = clientService.getExecutorAppointments(existingUser.getUserId(), existingUser.getUserType());
					request.getSession(true).setAttribute("appointments", appointments);
					rd = request.getRequestDispatcher("executorPage");
				}
			} else if(userType.toUpperCase().compareTo("NURSE") == 0) {
				request.getSession(true).setAttribute("appointments", clientService.getExecutorAppointments(existingUser.getUserId(), existingUser.getUserType()));
				rd = request.getRequestDispatcher("executorPage");
			} else {
				rd = request.getRequestDispatcher(Command.welcomePageJSP);
			}

			rd.forward(request, response);
		} catch (ServiceException se) {
			request.getSession(true).setAttribute(Command.answerAttribute, se.getMessage());
			logger.info(se);
			response.sendRedirect(Command.mainPageJSP);
		}
	}
	
	private void linkAttendedDoctorInfo(HttpServletRequest request, User doctor) {
		request.getSession(true).setAttribute("attended_doctor_fname", doctor.getFirstName());
		request.getSession(true).setAttribute("attended_doctor_lname", doctor.getLastName());
		request.getSession(true).setAttribute("attended_doctor_specialization", ((Doctor) doctor).getSpecialization());
	}

}
