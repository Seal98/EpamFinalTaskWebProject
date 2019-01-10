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
		<input class="reg-buttons" type="submit" style="float: left;" onclick="userLogOut();" id="backToMainPageButton"
			name="backToMainPageButton" value="Log out" /> <input
			type="hidden" id="requestParameter" name="requestParameter"
			value="-1">
	</form>
	<input class="reg-buttons" type="submit" onclick="userProfileInfo();" id="profileInfoButton"
			name="profileInfoButton" value="Profile info" /><br />
	<div style="width: 100%;">
		<div class="tableNameFont">Appointments:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">Patient</div>
					<div class="divTableHead">Appointee</div>
					<div class="divTableHead">Type</div>
					<div class="divTableHead">Treatment</div>
					<div class="divTableHead">Status</div>
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
							<input class="reg-buttons" type="submit" onclick="completeAppointment(this, <c:out value="${appointments.appointmentId}" />);" name="appointmentButton" id="<c:out value="${appointments.appointmentId}" />" value="Complete" /><br />
						</c:if>
						<c:if test="${appointments.completionStatus.compareTo('completed') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" name="appointmentButton" id="<c:out value="${appointments.appointmentId}" />" value="Completed" disabled/><br />
						</c:if>
						<c:if test="${appointments.completionStatus.compareTo('canceled') == 0}">
  							<input class="reg-buttons" style="background-color: #86E49C" type="submit" name="appointmentButton" id="<c:out value="${appointments.appointmentId}" />" value="Canceled" disabled/><br />
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
<div class="form-popup" id="ProfileInfo">
  	<form class="form-container">
    <h1>Profile info</h1>
      <label><b>Type:</b> <c:out value="${current_user.userType}" /></label><br>
      <label><b>First name:</b> <c:out value="${current_user.firstName}" /></label><br> 
      <label><b>Last name:</b> <c:out value="${current_user.lastName}" /></label><br>
      <c:if test="${current_user.userType.toUpperCase().compareTo('DOCTOR') == 0}">   
      <label><b>Specialization:</b> <c:out value="${current_user.specialization}" /></label><br> 
      <label><b>Experience:</b> <c:out value="${current_user.experience}" /></label><br> 
      </c:if>  
      <c:if test="${current_user.userType.toUpperCase().compareTo('NURSE') == 0}">
      <label><b>Experience:</b> <c:out value="${current_user.experience}" /></label><br> 
      </c:if>  
      <c:if test="${current_user.userType.toUpperCase().compareTo('PATIENT') == 0}">   
      <label><b>Admission date:</b> <c:out value="${current_user.admissionDate}" /></label><br> 
      <label><b>Attended doctor:</b> <c:out value="${current_user.attended_doctor_fname}" /> <c:out value="${current_user.attended_doctor_fname}" /></label><br> 
      </c:if>
    <button type="button" class="btn cancel" onclick="closeProfileForm();">Close</button>
  </form>
</div>	
	
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/script.js"></script>	
</body>
</html>