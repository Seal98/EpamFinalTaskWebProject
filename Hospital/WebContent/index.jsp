<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>MyJSP</title>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.enter.login" var="login_message" />
<fmt:message bundle="${loc}" key="locale.enter.password" var="password_message" />
<fmt:message bundle="${loc}" key="locale.sign.in" var="sign_in_message" />
<fmt:message bundle="${loc}" key="locale.sign.up" var="sign_up_message" />
<fmt:message bundle="${loc}" key="locale.locale.button.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale.locale.button.en" var="locale_en" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="css/style.css" rel='stylesheet' type='text/css'>
</head>
<body>
	<div class="auth-form">
	<form action="authorization" method="post">
		${login_message}:<br />
		<input type="text" name="login" value="" /><br /> ${password_message}:<br />
		<input type="password" name="password" value="" /><br />
		<div>
		<input class="auth-buttons" type="submit" name="signInButton" onclick="signIn();" value="${sign_in_message}" /><br />
		<input class="auth-buttons" type="submit" name="signUpButton" onclick="signUp();" value="${sign_up_message}" /><br />
		</div>
		<input type="hidden" id="requestParameter" name="requestParameter" value="-1">
	</form>
	<c:set var = "answer" scope = "session" value = "${sessionScope.answer}"/>
	<c:if test = "${answer != null}">
         <h3><c:out value="${answer}" /></h3>
    </c:if>
    </div>
    	<form action="setLocale" method="post">
		<input type="hidden" name="requestParameter" value="ru" />
		<input class="locale-buttons" type="submit" name="setRULocale" value="${locale_ru}" /><br />
		</form>
		<form action="setLocale" method="post">
		<input type="hidden" name="requestParameter" value="en" />
		<input class="locale-buttons" type="submit" name="setENLocale" value="${locale_en}" /><br />
		</form>
	<script src="js/script.js"></script>

	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
</body>
</html>