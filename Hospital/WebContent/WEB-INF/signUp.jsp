<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="createUser" method="post">
		<input type="hidden" name="command" value="forward" /> Enter login:<br />
		<input type="text" name="login" value="" /><br /> Enter password:<br />
		<input type="password" name="password" value="" /><br /> Confirm password:<br />
		<input type="password" name="passwordConfirm" value="" /><br /> 
		<input type="submit" onclick="confirmReg();" id="confirmRegButton" name="confirmRegButton" value="Sign up" /><br />
		<input type="submit" onclick="backToMain();" id="backToMainPageButton" name="backToMainPageButton" value="Back" /><br />
		<input type="hidden" id="requestParameter" name="requestParameter" value="-1">
	</form>
	<c:set var = "answer" scope = "session" value = "${requestScope.answer}"/>
	<c:if test = "${answer != null}">
         <h3><c:out value="${answer}" /></h3>
    </c:if>
	<script src="js/script.js"></script>
</body>
</html>