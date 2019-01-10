package by.epam.training.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import by.epam.training.web.bean.Appointment;
import by.epam.training.web.bean.MedicalTreatment;
import by.epam.training.web.bean.Patient;
import by.epam.training.web.bean.PatientCuringInfo;
import by.epam.training.web.bean.User;
import by.epam.training.web.dao.FactoryDAO;
import by.epam.training.web.dao.UserDAO;
import by.epam.training.web.exception.DAOException;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.validator.AppointmentValidator;
import by.epam.training.web.service.validator.UserValidator;
import by.epam.training.web.service.validator.ValidatorHelper;
import by.epam.training.web.service.validator.ValidatorResult;

public class ClientService {

	public User signIn(String login, String password) throws ServiceException {
		ValidatorResult result = ValidatorHelper.getUserValidator().userSignInDataValidator(login, password);
		User existingUser = null;
		if (!result.isValid()) {
			throw new ServiceException(result.getValidationMessage());
		}
		try {
			FactoryDAO factoryDao = FactoryDAO.getInstance();
			UserDAO userDao = factoryDao.getUserDAO();
			existingUser = userDao.signIn(login, password);
		} catch (DAOException daoE) {
			throw new ServiceException(daoE.getMessage(), daoE);
		}
		return existingUser;
	}

	public void signUp(String login, String password, String confirmedPassword, String firstName, String lastName,
			String birthdateStr, String therapistId) throws ServiceException {
		ValidatorResult result = ValidatorHelper.getUserValidator().userSignUpDataValidator(login, password,
				confirmedPassword, firstName, lastName, therapistId);
		if (!result.isValid()) {
			throw new ServiceException(result.getValidationMessage());
		}
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdate = format.parse(birthdateStr);
			userDao.registration(login, password, firstName, lastName, new java.sql.Date(birthdate.getTime()),
					new java.sql.Date(new Date().getTime()), Integer.parseInt(therapistId));
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (ParseException e) {
			throw new ServiceException("Input birthdate once again", e);
		}
	}

	public PatientCuringInfo getUserInfo(int userId) throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		PatientCuringInfo curingInfo = null;
		try {
			curingInfo = userDao.getUserInfo(userId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return curingInfo;
	}

	public List<Patient> getAttendedPatients(int therapistId) throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		List<Patient> attendedPatients = null;
		try {
			attendedPatients = userDao.getAttendedPatients(therapistId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return attendedPatients;
	}

	public List<User> getExecutors() throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		List<User> executors = null;
		try {
			executors = userDao.getExecutors();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return executors;
	}

	public List<MedicalTreatment> getTreatment() {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		List<MedicalTreatment> treatment = null;
		treatment = userDao.getMedicalTreatment();

		return treatment;
	}

	public List<MedicalTreatment> getMedicine(List<MedicalTreatment> treatment) {
		List<MedicalTreatment> medicine = new LinkedList<>();
		for (MedicalTreatment treat : treatment) {
			if (treat.getType().toUpperCase().compareTo("MEDICINE") == 0) {
				medicine.add(treat);
			}
		}
		return medicine;
	}

	public List<MedicalTreatment> getProcedures(List<MedicalTreatment> treatment) {
		List<MedicalTreatment> procedures = new LinkedList<>();
		for (MedicalTreatment treat : treatment) {
			if (treat.getType().toUpperCase().compareTo("PROCEDURE") == 0) {
				procedures.add(treat);
			}
		}
		return procedures;
	}

	public List<MedicalTreatment> getSurgeries(List<MedicalTreatment> treatment) {
		List<MedicalTreatment> surgeries = new LinkedList<>();
		for (MedicalTreatment treat : treatment) {
			if (treat.getType().toUpperCase().compareTo("SURGERY") == 0) {
				surgeries.add(treat);
			}
		}
		return surgeries;
	}

	public int createAppointment(String patientIdStr, String executorIdStr, String treatmentTypeStr,
			String treatmentStr, String doctorId) throws ServiceException {
		AppointmentValidator validator = ValidatorHelper.getAppointmentValidator();
		int appointmentId = -1;
		ValidatorResult result = validator.validate(patientIdStr, executorIdStr, treatmentTypeStr);
		if (!result.isValid()) {
			throw new ServiceException(result.getValidationMessage());
		}
		UserDAO userDao = FactoryDAO.getInstance().getUserDAO();
		int patientId = Integer.parseInt(patientIdStr.split("[a-zA-Z]+")[1]);
		int executorId = Integer.parseInt(executorIdStr);
		String treatmentType = treatmentTypeStr.split("Rb")[0];
		System.out.println(doctorId);
		try {
			appointmentId = userDao.createAppointment(patientId, executorId, treatmentType, treatmentStr, Integer.parseInt(doctorId));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return appointmentId;
	}

	public List<Appointment> getMadeAppointments(int therapistId) throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		List<Appointment> therapistAppointments = null;
		try {
			therapistAppointments = userDao.getTherapistAppointments(therapistId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return therapistAppointments;
	}

	public List<Appointment> getExecutorAppointments(int executorId, String executorType) throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		List<Appointment> appointments = null;
		try {
			if (executorType.toUpperCase().compareTo("DOCTOR") == 0) {
				appointments = userDao.getDoctorAppointments(executorId);
			} else if (executorType.toUpperCase().compareTo("NURSE") == 0) {
				appointments = userDao.getNurseAppointments(executorId);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return appointments;
	}

	public void completeAppointment(String appointmentId) throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		try {
			ValidatorResult result = ValidatorHelper.getAppointmentValidator().validateId(appointmentId);
			if(!result.isValid()) {
				throw new ServiceException(result.getValidationMessage());
			}
			userDao.completeAppointment(Integer.parseInt(appointmentId));
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void dischargePatient(String userId, String diagnosis, String finalDiagnosis) throws ServiceException {
			UserValidator validator = ValidatorHelper.getUserValidator();
			ValidatorResult result = validator.validateDischargeInfo(userId, diagnosis, finalDiagnosis);
			if(!result.isValid()) {
				throw new ServiceException(result.getValidationMessage());
			}
			try {
			FactoryDAO factoryDao = FactoryDAO.getInstance();
			UserDAO userDao = factoryDao.getUserDAO();
			userDao.dischargePatient(Integer.parseInt(userId), diagnosis, finalDiagnosis, new java.sql.Date(new Date().getTime()));
			} catch(DAOException e) {
				throw new ServiceException(e);
			}
	}
	
	public void cancelAppointment(String appointmentId) throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		try {
			ValidatorResult result = ValidatorHelper.getAppointmentValidator().validateId(appointmentId);
			if(!result.isValid()) {
				throw new ServiceException(result.getValidationMessage());
			}
			userDao.cancelAppointment(Integer.parseInt(appointmentId));
		} catch(DAOException e) {
			throw new ServiceException(e);
		}		
	}
	
	public List<User> getTherapists() throws ServiceException {
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		List<User> therapists = null;
		try {
			therapists = userDao.getTherapists();
		} catch(DAOException e) {
			throw new ServiceException(e);
		}	
		return therapists;
	}
	
}
