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
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		String login = request.getParameter(Command.loginParameter);
		String password = request.getParameter(Command.passwordParameter);
		if(login == null) {
			login = request.getSession(true).getAttribute(Command.loginParameter).toString();
			password = request.getSession(true).getAttribute(Command.passwordParameter).toString();
		}
		RequestDispatcher rd = null;
		try {
			User existingUser = clientService.signIn(login, password);
			request.getSession(true).setAttribute(Command.currentUser, existingUser);
			String userType = existingUser.getClass().getSimpleName();
			request.setAttribute(Command.userType, userType);
			request.getSession(true).setAttribute(Command.loginParameter, existingUser.getUserLogin());
			request.getSession(true).setAttribute(Command.passwordParameter, existingUser.getUserPassword());
			request.getSession(true).setAttribute(Command.idParameter, existingUser.getUserId());
			if(existingUser.getLanguage().compareTo(Command.notDefinedLanguage) != 0) {
				request.getSession(true).setAttribute(Command.localeParameter, existingUser.getLanguage());
			}
			if (userType.toUpperCase().compareTo(Command.patientUpperCase) == 0) {
				PatientCuringInfo curingInfo = clientService.getUserInfo(existingUser.getUserId());
				request.setAttribute(appointments, curingInfo.getAppointments());
				linkAttendedDoctorInfo(request, curingInfo.getAttendedDoctor());
				rd = request.getRequestDispatcher(patientPageJSP);
				request.getSession(true).setAttribute(Command.currentPageParameter, Command.patientPageJSP);
			} else if (userType.toUpperCase().compareTo(Command.doctorUpperCase) == 0) {
				if(((Doctor)existingUser).getSpecialization().toUpperCase().compareTo(Command.therapistUpperCase) == 0) {
					request.setAttribute(Command.attendedPatients, clientService.getAttendedPatients(existingUser.getUserId()));
					request.setAttribute(Command.executors, clientService.getExecutors());
					List<MedicalTreatment> treatment = clientService.getTreatment();
					request.setAttribute(Command.medicine, clientService.getMedicine(treatment));
					request.setAttribute(Command.procedures, clientService.getProcedures(treatment));
					request.setAttribute(Command.surgeries, clientService.getSurgeries(treatment));
					request.setAttribute(Command.appointments, clientService.getMadeAppointments(existingUser.getUserId()));
					rd = request.getRequestDispatcher(Command.therapistPageJSP);
					request.getSession(true).setAttribute(Command.currentPageParameter, Command.therapistPageJSP);
				} else {
					List<Appointment> appointments = clientService.getExecutorAppointments(existingUser.getUserId(), existingUser.getUserType());
					request.setAttribute(Command.appointments, appointments);
					rd = request.getRequestDispatcher(Command.executorPageJSP);
					request.getSession(true).setAttribute(Command.currentPageParameter, Command.executorPageJSP);
				}
			} else if(userType.toUpperCase().compareTo(Command.nurseUpperCase) == 0) {
				request.setAttribute(Command.appointments, clientService.getExecutorAppointments(existingUser.getUserId(), existingUser.getUserType()));
				rd = request.getRequestDispatcher(Command.executorPageJSP);
				request.getSession(true).setAttribute(Command.currentPageParameter, Command.executorPageJSP);
			} else {
				rd = request.getRequestDispatcher(Command.welcomePageJSP);
				request.getSession(true).setAttribute(Command.currentPageParameter, Command.welcomePageJSP);
			}

			rd.forward(request, response);
		} catch (ServiceException se) {
			request.setAttribute(Command.answerAttribute, se.getMessage());
			logger.error(se);
			request.getRequestDispatcher(Command.mainPageJSP).forward(request, response);
			
		}
	}
	
	private void linkAttendedDoctorInfo(HttpServletRequest request, User doctor) {
		request.setAttribute(Command.attendedDoctorFName, doctor.getFirstName());
		request.setAttribute(Command.attendedDoctorLName, doctor.getLastName());
		request.setAttribute(Command.attendedDoctorSpecialization, ((Doctor) doctor).getSpecialization());
	}

}
