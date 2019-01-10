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
		<div class="tableNameFont">Attended doctor:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">First name</div>
					<div class="divTableHead">Last name</div>
					<div class="divTableHead">Specialization</div>
				</div>
			</div>
			<div class="divTableBody">
				<div class="divTableRow">
					<div class="divTableCell">
						<c:out value="${requestScope.attended_doctor_fname}" />
					</div>
					<div class="divTableCell">
						<c:out value="${requestScope.attended_doctor_lname}" />
					</div>
					<div class="divTableCell">
						<c:out value="${requestScope.attended_doctor_specialization}" />
					</div>
				</div>
			</div>
		</div>
	</div><br>
	<div style="width: 100%;">
		<div class="tableNameFont">Appointments:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">Appointee</div>
					<div class="divTableHead">Executor</div>
					<div class="divTableHead">Treatment</div>
					<div class="divTableHead">Status</div>
				</div>
			</div>
			<div class="divTableBody">
				<c:forEach var="apps" items="${requestScope.appointments}">
				<div class="divTableRow">
					<div class="divTableCell">
						<c:out value="${apps.appointee.firstName}" /> <c:out value="${apps.appointee.lastName}" />
					</div>
					<div class="divTableCell">
						<c:out value="${apps.appointmentExecutor.userType}" /> <c:out value="${apps.appointmentExecutor.firstName}" /> <c:out value="${apps.appointmentExecutor.lastName}" />
					</div>
					<div class="divTableCell">
						<c:out value="${apps.treatment.name}" />
					</div>
					<div class="divTableCell">
						<c:out value="${apps.completionStatus}" />
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
	</div><br>
	<c:if test="${current_user.dischargeStatus == true}">
		<div style="width: 100%;">
		<div class="tableNameFont">Discharge info:</div>
		<div class="divTable greenTable">
			<div class="divTableHeading">
				<div class="divTableRow">
					<div class="divTableHead">Diagnosis</div>
					<div class="divTableHead">Final diagnosis</div>
					<div class="divTableHead">Discharge date</div>
				</div>
			</div>
			<div class="divTableBody">
				<div class="divTableRow">
					<div class="divTableCell">
						<c:out value="${current_user.dischargeInfo.diagnosis}" />
					</div>
					<div class="divTableCell">
						<c:out value="${current_user.dischargeInfo.finalDiagnosis}" />
					</div>
					<div class="divTableCell">
						<c:out value="${current_user.dischargeInfo.dischargeDate}" />
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:if>

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
      <label><b>Attended doctor:</b> <c:out value="${requestScope.attended_doctor_fname}" /> <c:out value="${requestScope.attended_doctor_lname}" /></label><br> 
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