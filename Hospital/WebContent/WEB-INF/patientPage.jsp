<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/style.css" rel='stylesheet' type='text/css'>
</head>
<body>
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
						<c:out value="${sessionScope.attended_doctor_fname}" />
					</div>
					<div class="divTableCell">
						<c:out value="${sessionScope.attended_doctor_lname}" />
					</div>
					<div class="divTableCell">
						<c:out value="${sessionScope.attended_doctor_specialization}" />
					</div>
				</div>
			</div>
		</div>
	</div>
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
				<c:forEach var="apps" items="${sessionScope.appointments}">
				<div class="divTableRow">
					<div class="divTableCell">
						<c:out value="${apps.appointee.firstName}" /> <c:out value="${apps.appointee.lastName}" />
					</div>
					<div class="divTableCell">
						<c:out value="${apps.appointmentExecutor.firstName}" /> <c:out value="${apps.appointmentExecutor.lastName}" />
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
	</div>
</body>
</html>