<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="css/style.css" rel='stylesheet' type='text/css'>
</head>
<body>
	<form action="createUser" method="post">
		<input class="reg-buttons" type="submit" onclick="userLogOut();" id="backToMainPageButton"
			name="backToMainPageButton" value="Log out" /><br /> <input
			type="hidden" id="requestParameter" name="requestParameter"
			value="-1">
	</form>
	
	<div style="width: 100%;">
		<div class="tableNameFont">Attended patients:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">First name</div>
					<div class="divTableHead">Last name</div>
					<div class="divTableHead">Birthdate</div>
					<div class="divTableHead">Admission date</div>
				</div>
			</div>
			<div class="divTableBody">
				<c:forEach var="patients" items="${sessionScope.attended_patients}">
				<c:if test="${patients.dischargeStatus == false}">
					<div class="divTableRow" id="rowAttended<c:out value="${patients.userId}" />">
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />f"><c:out value="${patients.firstName}" /></div>
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />l"><c:out value="${patients.lastName}" /></div>
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />bd"><c:out value="${patients.birthDate}" /></div>
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />ad"><c:out value="${patients.admissionDate}" /></div>
						<div class="divTableCell">
						<input class="reg-buttons" type="submit" onclick="openForm(this);" id="appointment<c:out value="${patients.userId}" />" name="appointmentButton" value="Appoint" /><br />
						</div>
						<div class="divTableCell">
						<input class="reg-buttons" type="submit" onclick="openDischargeForm(this);" id="discharge<c:out value="${patients.userId}" />" name="dischargeButton" value="Discharge" /><br />
						</div>
					</div>
				</c:if>
				</c:forEach>
			</div>
		</div>
	</div><br>
	<div style="width: 100%;">
		<div class="tableNameFont">Discharged patients:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">First name</div>
					<div class="divTableHead">Last name</div>
					<div class="divTableHead">Birthdate</div>
					<div class="divTableHead">Admission date</div>
					<div class="divTableHead">Discharge date</div>
					<div class="divTableHead">Diagnosis</div>
					<div class="divTableHead">Final diagnosis</div>															
				</div>
			</div>
			<div class="divTableBody" id="dischargedPatientsTable">
				<c:forEach var="patients" items="${sessionScope.attended_patients}">
				<c:if test="${patients.dischargeStatus == true}">
					<div class="divTableRow">
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />f"><c:out value="${patients.firstName}" /></div>
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />l"><c:out value="${patients.lastName}" /></div>
						<div class="divTableCell"><c:out value="${patients.birthDate}" /></div>
						<div class="divTableCell"><c:out value="${patients.admissionDate}" /></div>
						<div class="divTableCell"><c:out value="${patients.dischargeInfo.dischargeDate}" /></div>
						<div class="divTableCell"><c:out value="${patients.dischargeInfo.diagnosis}" /></div>
						<div class="divTableCell"><c:out value="${patients.dischargeInfo.finalDiagnosis}" /></div>
					</div>
				</c:if>
				</c:forEach>
			</div>
		</div>
	</div>	
	<br>
	<div style="width: 100%;">
		<div class="tableNameFont">Made appointments:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">Patient</div>
					<div class="divTableHead">Executor</div>
					<div class="divTableHead">Treatment type</div>
					<div class="divTableHead">Treatment</div>
					<div class="divTableHead">Status</div>
				</div>
			</div>
			<div id="therapistAppointments" class="divTableBody">
				<c:forEach var="apps" items="${sessionScope.appointments}">
					<div class="divTableRow">
						<div class="divTableCell">
							<c:out value="${apps.patient.firstName}" /> <c:out value="${apps.patient.lastName}" />
						</div>
						<div class="divTableCell">
							<c:out value="${apps.appointmentExecutor.firstName}" /> <c:out value="${apps.appointmentExecutor.lastName}" />
						</div>
						<div class="divTableCell">
							<c:out value="${apps.treatment.type}" />
						</div>
						<div class="divTableCell">
							<c:out value="${apps.treatment.name}" />
						</div>
						<div class="divTableCell" id="statusCell<c:out value="${apps.appointmentId}" />">
							<c:out value="${apps.completionStatus}" />
						</div>
						<div class="divTableCell">
						<c:if test="${apps.completionStatus.compareTo('not completed') == 0}">
							<input class="reg-buttons" type="submit" onclick="cancelAppointment(this, <c:out value="${apps.appointmentId}" />);" id="cancelButton<c:out value="${apps.appointmentId}" />" value="Cancel" /><br />
						</c:if>
						<c:if test="${apps.completionStatus.compareTo('completed') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" value="Completed" disabled/><br />
						</c:if>
						<c:if test="${apps.completionStatus.compareTo('canceled') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" value="Canceled" disabled/><br />
						</c:if>	
						</div>				
					</div>
				</c:forEach>
			</div>
		</div>
	</div>	
	<div class="form-popup" id="myForm">
  	<form class="form-container">
    <h1>Appointment</h1>
    <label for="dropBoxExecutor"><b>Executor:  </b></label>
		<select id="executorDb" name="dropBoxExecutor" class="selectpicker">
		<option value="defOpt" disabled selected value> -- select an option -- </option>
	  			<c:forEach var="executors" items="${sessionScope.executors}">
	  				<option value="<c:out value="${executors.userType}" /> <c:out value="${executors.userId}" /> <c:out value="${executors.firstName}" /> <c:out value="${executors.lastName}" />"><c:out value="${executors.userType}" /> <c:out value="${executors.firstName}" /> <c:out value="${executors.lastName}" /></option>
				</c:forEach>
		</select></br>

	  <input type="radio" id="procedureRb" name="treatmentRbs" onclick="handleTreatmentRb(this);" value="procedureRb" disabled>
  	  <label for="procedureRb">Procedure </label>
	  <input type="radio" id="surgeryRb" name="treatmentRbs" onclick="handleTreatmentRb(this);" value="surgeryRb" disabled>
  	  <label for="surgeryRb">Surgery </label>
	  <input type="radio" id="medicineRb" name="treatmentRbs" onclick="handleTreatmentRb(this);" value="medicineRb" disabled>
  	  <label for="medicineRb">Medicine </label>

		<label id="medicineLabel" for="dropBoxMedicine" style="display: none;"><b>Medicine:  </b></label>
		<select id="medicineDb" name="dropBoxMedicine" class="selectpicker" style="display: none;">
		<option value="defOpt" disabled selected value> -- select an option -- </option>		
	  		<c:forEach var="medicine" items="${sessionScope.medicine}">
	  			<option><c:out value="${medicine.name}" /></option>
			</c:forEach>
		</select></br>
		<label id="proceduresLabel" for="dropBoxProcedures" style="display: none;"><b>Procedures:  </b></label>
		<select id="proceduresDb" name="dropBoxProcedures" class="selectpicker" style="display: none;">
		<option value="defOpt" disabled selected value> -- select an option -- </option>		
	  		<c:forEach var="procedures" items="${sessionScope.procedures}">
	  			<option><c:out value="${procedures.name}" /></option>
			</c:forEach>
		</select></br>		
		<label id="surgeriesLabel" for="dropBoxSurgeries" style="display: none;"><b>Surgeries:  </b></label>
		<select id="surgeriesDb" name="dropBoxSurgeries" class="selectpicker" style="display: none;">
		<option value="defOpt" disabled selected value> -- select an option -- </option>		
	  		<c:forEach var="surgeries" items="${sessionScope.surgeries}">
	  			<option><c:out value="${surgeries.name}" /></option>
			</c:forEach>
		</select></br>		
    <button type="button" class="btn" onclick="makeAppointment();">Appoint</button>
    <button type="button" class="btn cancel" onclick="closeForm();">Close</button>
  </form>
</div>

<div class="form-popup" id="myDischargeForm">
  	<form class="form-container">
    <h1>Discharge form</h1>
      <label for="diagnosisInput">Diagnosis</label>
	  <input type="text" id="diagnosis" name="diagnosisInput">
  	  <label for="finalDiagnosisInput">Final diagnosis</label>
	  <input type="text" id="finalDiagnosis" name="finalDiagnosisInput">
    <button type="button" class="btn" onclick="dischargePatient();">Discharge</button>
    <button type="button" class="btn cancel" onclick="closeDischargeForm();">Close</button>
  </form>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/script.js"></script>
</body>
</html>