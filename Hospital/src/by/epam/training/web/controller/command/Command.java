package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;

public interface Command {
	
	public static final String mainPageJSP = "index.jsp";
	public static final String welcomePageJSP = "welcome";
	public static final String signUpPageJSP = "signUp";
	public static final String answerAttribute = "answer";
	public static final String loginParameter = "login";
	public static final String idParameter = "id";
	public static final String doctorIdParameter = "doctorId";
	public static final String passwordParameter = "password";
	public static final String firstNameParameter = "fname";
	public static final String lastNameParameter = "lname";
	public static final String birthDateParameter = "bdate";
	public static final String therapistParameter = "therapistParameter";
	public static final String passwordConfirmParameter = "passwordConfirm";
	public static final String registrationConfirmedMessage = "You have been registered";
	public static final String wrongCommandMessage = "Something went wrong";
	public static final String userType = "userType";
	public static final String currentUser = "current_user";
	public static final String patientUpperCase = "PATIENT";
	public static final String doctorUpperCase = "DOCTOR";
	public static final String nurseUpperCase = "NURSE";
	public static final String therapistUpperCase = "THERAPIST";
	public static final String appointments = "appointments";
	public static final String patientPageJSP = "patientPage";
	public static final String attendedPatients = "attended_patients";
	public static final String medicine = "medicine";
	public static final String procedures = "procedures";
	public static final String surgeries = "surgeries";
	public static final String executors = "executors";
	public static final String therapistPageJSP = "therapistPage";
	public static final String executorPageJSP = "executorPage";
	public static final String attendedDoctorFName = "attended_doctor_fname";
	public static final String attendedDoctorLName = "attended_doctor_lname";
	public static final String attendedDoctorSpecialization = "attended_doctor_specialization";
	public static final String changeLocaleParameter = "changeLocaleParameter";
	public static final String requestParameter = "requestParameter";
	public static final String currentPageParameter = "currentPage";
	public static final String localeParameter = "locale";
	public static final String authorizationParameter = "authorization";
	public static final String signInParameter = "SIGN_IN";
	public static final String appointmentIdParameter = "appointmentId";
	public static final String successMessage = "success";
	public static final String patientIdParameter = "patientId";
	public static final String executorIdParameter = "executorId";
	public static final String treatmentTypeParameter = "selectedTreatmentType";
	public static final String treatmentParameter = "selectedTreatment";
	public static final String creationFailedMessage = "creation failed";
	public static final String regMistakeMessageParameter = "regMistakeMessage";
	public static final String userIdParameter = "userId";
	public static final String diagnosisParameter = "diagnosis";
	public static final String finalDiagnosisParameter = "finalDiagnosis";
	public static final String therapistsParameter = "therapists";
	public static final String notDefinedLanguage = "not defined";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException;
}
