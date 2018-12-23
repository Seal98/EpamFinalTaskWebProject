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
<fmt:message bundle="${loc}" key="locale.enter.login" var="login_message" />
<fmt:message bundle="${loc}" key="locale.enter.password" var="password_message" />
<fmt:message bundle="${loc}" key="locale.confirm.password" var="confirm_password_message" />
<fmt:message bundle="${loc}" key="locale.first.name" var="first_name_message" />
<fmt:message bundle="${loc}" key="locale.last.name" var="last_name_message" />
<fmt:message bundle="${loc}" key="locale.birthdate" var="birthdate_message" />
<fmt:message bundle="${loc}" key="locale.sign.up" var="sign_up_message" />
<fmt:message bundle="${loc}" key="locale.back.to.main.page" var="back_message" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="css/style.css" rel='stylesheet' type='text/css'>
</head>
<body>
<div class="reg-form">
	<form action="createUser" method="post">
		${first_name_message}: <br />
		<input type="text" name="fname" value="" /><br /> ${last_name_message}: <br />
		<input type="text" name="lname" value="" /><br /> ${birthdate_message}: <br />
		<input type="date" name="bdate" value="" /><br /> ${login_message}:<br />
		<input type="text" name="login" value="" /><br /> ${password_message}:<br />
		<input type="password" name="password" value="" /><br /> ${confirm_password_message}:<br />
		<input type="password" name="passwordConfirm" value="" /><br /> 
		<input class="reg-buttons" type="submit" onclick="confirmReg();" id="confirmRegButton" name="confirmRegButton" value="${sign_up_message}" /><br />
		<input class="reg-buttons" type="submit" onclick="backToMain();" id="backToMainPageButton" name="backToMainPageButton" value="${back_message}" /><br />
		<input type="hidden" id="requestParameter" name="requestParameter" value="-1">
	</form>
		<c:set var = "answer" scope = "session" value = "${sessionScope.answer}"/>
	<c:if test = "${answer != null}">
         <h3><c:out value="${answer}" /></h3>
    </c:if>
</div>	
	<script src="js/script.js"></script>
	<script src="js/script.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
</body>
</html>