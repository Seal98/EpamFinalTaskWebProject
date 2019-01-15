<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.locale.button.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale.locale.button.en" var="locale_en" />
<fmt:message bundle="${loc}" key="locale.attention.patients" var="attendedPatients" />
<fmt:message bundle="${loc}" key="locale.attention.patient.fname" var="firstName" />
<fmt:message bundle="${loc}" key="locale.attention.patient.lname" var="lastName" />
<fmt:message bundle="${loc}" key="locale.attention.patient.birthday" var="birthDate" />
<fmt:message bundle="${loc}" key="locale.attention.patient.admissiondate" var="admissionDate" />
<fmt:message bundle="${loc}" key="locale.attention.patients.discharged" var="dischargedPatients" />
<fmt:message bundle="${loc}" key="locale.attention.patient.dischargedate" var="dischargeDate" />
<fmt:message bundle="${loc}" key="locale.attention.patient.diagnosis" var="diagnosis" />
<fmt:message bundle="${loc}" key="locale.attention.patient.diagnosis.final" var="finalDiagnosis" />
<fmt:message bundle="${loc}" key="locale.appointmemts" var="madeAppointments" />
<fmt:message bundle="${loc}" key="locale.appointment.patient" var="patient" />
<fmt:message bundle="${loc}" key="locale.appointment.executor" var="executor" />
<fmt:message bundle="${loc}" key="locale.appointment.treatment.type" var="treatmentType" />
<fmt:message bundle="${loc}" key="locale.appointment.treatment" var="treatment" />
<fmt:message bundle="${loc}" key="locale.appointment.status" var="status" />
<fmt:message bundle="${loc}" key="locale.creator.appointment" var="appointment" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.procedure" var="procedure" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.surgery" var="surgery" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.medicine" var="medicine" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.chosen.procedures" var="chosenProcedures" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.chosen.medicine" var="chosenMedicine" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.chosen.surgeries" var="chosenSurgeries" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.appoint" var="appointButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.discharge" var="dischargeButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.back" var="backButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.cancel" var="cancelButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.canceled" var="canceledButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.completed" var="completedButton" />
<fmt:message bundle="${loc}" key="locale.attention.patient.discharge.form" var="dischargeForm" />
<fmt:message bundle="${loc}" key="locale.user.profile" var="userProfile" />
<fmt:message bundle="${loc}" key="locale.user.logout" var="userLogOut" />
<fmt:message bundle="${loc}" key="locale.user.experience" var="experience" />
<fmt:message bundle="${loc}" key="locale.user.specialization" var="specialization" />
<fmt:message bundle="${loc}" key="locale.user.type" var="userType" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="css/style.css" rel='stylesheet' type='text/css'>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.css">
<script src="js/bootstrap.js" type="text/javascript"></script>

</head>
<body>
<div class="hospital-header">
<img src="http://www.pngall.com/wp-content/uploads/2016/06/Doctor-Symbol-Caduceus-PNG-Clipart.png" width="10%" height="100%">
	<form action="userAction" style="float: right;" method="post">
	<input type="hidden" id="requestParameter" name="requestParameter" value="-1">
	<input class="header-buttons" type="submit" onclick="userLogOut();" id="backToMainPageButton"
			name="backToMainPageButton" value="${userLogOut}" />
	</form>
	<input class="header-buttons" type="submit" style="float: right;" onclick="userProfileInfo();" id="profileInfoButton"
			name="profileInfoButton" value="${userProfile}" /><br />
</div>
<div class="form-profile" id="ProfileInfo">
  	<div class="form-container">
    <h1>${userProfile}</h1>
      <label><b>${userType}:</b> <c:out value="${current_user.userType}" /></label><br>
      <label><b>${firstName}:</b> <c:out value="${current_user.firstName}" /></label><br> 
      <label><b>${lastName}:</b> <c:out value="${current_user.lastName}" /></label><br>
      <c:if test="${current_user.userType.toUpperCase().compareTo('DOCTOR') == 0}">   
      <label><b>${specialization}:</b> <c:out value="${current_user.specialization}" /></label><br> 
      <label><b>${experience}:</b> <c:out value="${current_user.experience}" /></label><br> 
      </c:if>  
      <c:if test="${current_user.userType.toUpperCase().compareTo('NURSE') == 0}">
      <label><b>${experience}:</b> <c:out value="${current_user.experience}" /></label><br> 
      </c:if>  
      <c:if test="${current_user.userType.toUpperCase().compareTo('PATIENT') == 0}">   
      <label><b>${admissionDate}:</b> <c:out value="${current_user.admissionDate}" /></label><br> 
      <label><b>${appointor}:</b> <c:out value="${requestScope.attended_doctor_fname}" /> <c:out value="${requestScope.attended_doctor_lname}" /></label><br> 
       </c:if>	
	<form action="userAction" method="post">
	<input type="hidden" id="localeRequestParameter" name="requestParameter" value="-1">
    <input class="reg-buttons" type="submit" onclick="setLocaleRu();"  name="setRULocale" value="${locale_ru}" /><br />
	<input class="reg-buttons" type="submit" onclick="setLocaleEn();"  name="setENLocale" value="${locale_en}" /><br />
  	</form>
    <button type="button" class="btn cancel" onclick="closeProfileForm();">${backButton}</button>
  </div>
</div><br>
	<div style="width: 100%;">
		<div class="tableNameFont">${attendedPatients}:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">${firstName}</div>
					<div class="divTableHead">${lastName}</div>
					<div class="divTableHead">${birthDate}</div>
					<div class="divTableHead">${admissionDate}</div>
				</div>
			</div>
			<div class="divTableBody">
				<c:forEach var="patients" items="${requestScope.attended_patients}">
				<c:if test="${patients.dischargeStatus == false}">
					<div class="divTableRow" id="rowAttended<c:out value="${patients.userId}" />">
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />f"><c:out value="${patients.firstName}" /></div>
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />l"><c:out value="${patients.lastName}" /></div>
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />bd"><c:out value="${patients.birthDate}" /></div>
						<div class="divTableCell" id="p<c:out value="${patients.userId}" />ad"><c:out value="${patients.admissionDate}" /></div>
						<div class="divTableCell">
						<input class="reg-buttons" type="submit" onclick="openForm(this);" id="appointment<c:out value="${patients.userId}" />" name="appointmentButton" value="<c:out value="${appointButton}" />" /><br />
						</div>
						<div class="divTableCell">
						<input class="reg-buttons" type="submit" onclick="openDischargeForm(this);" id="discharge<c:out value="${patients.userId}" />" name="dischargeButton" value="<c:out value="${dischargeButton}" />" /><br />
						</div>
					</div>
				</c:if>
				</c:forEach>
			</div>
		</div>
	</div><br>
	<div style="width: 100%;">
		<div class="tableNameFont">${dischargedPatients}:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">${firstName}</div>
					<div class="divTableHead">${lastName}</div>
					<div class="divTableHead">${birthDate}</div>
					<div class="divTableHead">${admissionDate}</div>
					<div class="divTableHead">${dischargeDate}</div>
					<div class="divTableHead">${diagnosis}</div>
					<div class="divTableHead">${finalDiagnosis}</div>															
				</div>
			</div>
			<div class="divTableBody" id="dischargedPatientsTable">
				<c:forEach var="patients" items="${requestScope.attended_patients}">
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
		<div class="tableNameFont">${madeAppointments}:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">${patient}</div>
					<div class="divTableHead">${executor}</div>
					<div class="divTableHead">${treatmentType}</div>
					<div class="divTableHead">${treatment}</div>
					<div class="divTableHead">${status}</div>
				</div>
			</div>
			<div id="therapistAppointments" class="divTableBody">
				<c:forEach var="apps" items="${requestScope.appointments}">
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
							<input class="reg-buttons" type="submit" onclick="cancelAppointment(this, <c:out value="${apps.appointmentId}" />, '<c:out value="${canceledButton}" />');" id="cancelButton<c:out value="${apps.appointmentId}" />" value="<c:out value="${cancelButton}" />" /><br />
						</c:if>
						<c:if test="${apps.completionStatus.compareTo('completed') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" value="<c:out value="${completedButton}" />" disabled/><br />
						</c:if>
						<c:if test="${apps.completionStatus.compareTo('canceled') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" value="<c:out value="${canceledButton}" />" disabled/><br />
						</c:if>	
						</div>				
					</div>
				</c:forEach>
			</div>
		</div>
	</div>	
	<div class="form-popup" id="myForm">
  	<form class="form-container">
    <h1>${appointment}</h1>
    <label for="dropBoxExecutor"><b>${executor}:  </b></label>
		<select id="executorDb" name="dropBoxExecutor" class="selectpicker">
		<option value="defOpt" disabled selected value> -- select an option -- </option>
	  			<c:forEach var="executors" items="${requestScope.executors}">
	  				<option value="<c:out value="${executors.userType}" /> <c:out value="${executors.userId}" /> <c:out value="${executors.firstName}" /> <c:out value="${executors.lastName}" />"><c:out value="${executors.userType}" /> <c:out value="${executors.firstName}" /> <c:out value="${executors.lastName}" /></option>
				</c:forEach>
		</select></br>

	  <input type="radio" id="procedureRb" name="treatmentRbs" onclick="handleTreatmentRb(this);" value="procedureRb" disabled>
  	  <label for="procedureRb">${procedure} </label>
	  <input type="radio" id="surgeryRb" name="treatmentRbs" onclick="handleTreatmentRb(this);" value="surgeryRb" disabled>
  	  <label for="surgeryRb">${surgery} </label>
	  <input type="radio" id="medicineRb" name="treatmentRbs" onclick="handleTreatmentRb(this);" value="medicineRb" disabled>
  	  <label for="medicineRb">${medicine} </label>

		<label id="medicineLabel" for="dropBoxMedicine" style="display: none;"><b>${chosenMedicine}:  </b></label>
		<select id="medicineDb" name="dropBoxMedicine" class="selectpicker" style="display: none;">
		<option value="defOpt" disabled selected value> -- select an option -- </option>		
	  		<c:forEach var="medicine" items="${requestScope.medicine}">
	  			<option><c:out value="${medicine.name}" /></option>
			</c:forEach>
		</select></br>
		<label id="proceduresLabel" for="dropBoxProcedures" style="display: none;"><b>${chosenProcedures}:  </b></label>
		<select id="proceduresDb" name="dropBoxProcedures" class="selectpicker" style="display: none;">
		<option value="defOpt" disabled selected value> -- select an option -- </option>		
	  		<c:forEach var="procedures" items="${requestScope.procedures}">
	  			<option><c:out value="${procedures.name}" /></option>
			</c:forEach>
		</select></br>		
		<label id="surgeriesLabel" for="dropBoxSurgeries" style="display: none;"><b>${chosenSurgeries}:  </b></label>
		<select id="surgeriesDb" name="dropBoxSurgeries" class="selectpicker" style="display: none;">
		<option value="defOpt" disabled selected value> -- select an option -- </option>		
	  		<c:forEach var="surgeries" items="${requestScope.surgeries}">
	  			<option><c:out value="${surgeries.name}" /></option>
			</c:forEach>
		</select></br>		
    <button type="button" class="btn" onclick="makeAppointment(<c:out value="${current_user.userId}" />, '<c:out value="${cancelButton}"/>', '<c:out value="${canceledButton}"/>');">${appointButton}</button>
    <button type="button" class="btn cancel" onclick="closeForm();">${backButton}</button>
  </form>
</div>

<div class="form-popup" id="myDischargeForm">
  	<form class="form-container">
    <h1>${dischargeForm}</h1>
      <label for="diagnosisInput"><b>${diagnosis}</b></label>
	  <input type="text" id="diagnosis" name="diagnosisInput">
  	  <label for="finalDiagnosisInput"><b>${finalDiagnosis}</b></label>
	  <input type="text" id="finalDiagnosis" name="finalDiagnosisInput">
    <button type="button" class="btn" onclick="dischargePatient();">${dischargeButton}</button>
    <button type="button" class="btn cancel" onclick="closeDischargeForm();">${backButton}</button>
  </form>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/script.js"></script>
<script src="js/scriptLocale.js"></script>
</body>
</html>