<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.locale.button.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale.locale.button.en" var="locale_en" />
<fmt:message bundle="${loc}" key="locale.attention.patient.fname" var="firstName" />
<fmt:message bundle="${loc}" key="locale.attention.patient.lname" var="lastName" />
<fmt:message bundle="${loc}" key="locale.attention.patient.birthday" var="birthDate" />
<fmt:message bundle="${loc}" key="locale.attention.patient.admissiondate" var="admissionDate" />
<fmt:message bundle="${loc}" key="locale.attention.patient.dischargedate" var="dischargeDate" />
<fmt:message bundle="${loc}" key="locale.appointment.patient" var="patient" />
<fmt:message bundle="${loc}" key="locale.appointment.executor" var="executor" />
<fmt:message bundle="${loc}" key="locale.appointment.treatment.type" var="treatmentType" />
<fmt:message bundle="${loc}" key="locale.appointment.treatment" var="treatment" />
<fmt:message bundle="${loc}" key="locale.appointment.status" var="status" />
<fmt:message bundle="${loc}" key="locale.patient.appointments" var="patientAppointments" />
<fmt:message bundle="${loc}" key="locale.patient.appointor" var="appointor" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.back" var="backButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.completed" var="completedButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.complete" var="completeButton" />
<fmt:message bundle="${loc}" key="locale.creator.appointment.button.canceled" var="canceledButton" />
<fmt:message bundle="${loc}" key="locale.user.profile" var="userProfile" />
<fmt:message bundle="${loc}" key="locale.user.logout" var="userLogOut" />
<fmt:message bundle="${loc}" key="locale.user.experience" var="experience" />
<fmt:message bundle="${loc}" key="locale.user.specialization" var="specialization" />
<fmt:message bundle="${loc}" key="locale.user.type" var="userType" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="css/style.css" rel='stylesheet' type='text/css'>
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
		<div class="tableNameFont">${patientAppointments}:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">${patient}</div>
					<div class="divTableHead">${appointor}</div>
					<div class="divTableHead">${treatmentType}</div>
					<div class="divTableHead">${treatment}</div>
					<div class="divTableHead">${status}</div>
				</div>
			</div>
			<div class="divTableBody">
				<c:forEach var="appointments" items="${requestScope.appointments}">
					<div class="divTableRow">
						<div class="divTableCell"><c:out value="${appointments.patient.firstName}" /> <c:out value="${appointments.patient.lastName}" /></div>
						<div class="divTableCell"><c:out value="${appointments.appointee.firstName}" /> <c:out value="${appointments.appointee.lastName}" /></div>
						<div class="divTableCell"><c:out value="${appointments.treatment.type}" /></div>
						<div class="divTableCell"><c:out value="${appointments.treatment.name}" /></div>
						<div class="divTableCell" id="status<c:out value="${appointments.appointmentId}" />"><c:out value="${appointments.completionStatus}" /></div>
						<c:if test="${appointments.completionStatus.compareTo('not completed') == 0}">
							<input class="reg-buttons" type="submit" onclick="completeAppointment(this, <c:out value="${appointments.appointmentId}" />, '<c:out value="${completedButton}" />');" name="appointmentButton" id="<c:out value="${appointments.appointmentId}" />" value="${completeButton}" /><br />
						</c:if>
						<c:if test="${appointments.completionStatus.compareTo('completed') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" name="appointmentButton" id="<c:out value="${appointments.appointmentId}" />" value="${completedButton}" disabled/><br />
						</c:if>
						<c:if test="${appointments.completionStatus.compareTo('canceled') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" name="appointmentButton" id="<c:out value="${appointments.appointmentId}" />" value="${canceledButton}" disabled/><br />
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
<div class="form-popup" id="ProfileInfo">
  	<form class="form-container">
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
      <label><b>${appointor}:</b> <c:out value="${current_user.attended_doctor_fname}" /> <c:out value="${current_user.attended_doctor_fname}" /></label><br> 
      </c:if>
    <button type="button" class="btn cancel" onclick="closeProfileForm();">${backButton}</button>
  </form>
</div>	
	
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/script.js"></script>	
<script src="js/scriptLocale.js"></script>
</body>
</html>