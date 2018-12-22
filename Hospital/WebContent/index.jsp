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

</head>
<body>
	<form action="authorization" method="post">
		${login_message}:<br />
		<input type="text" name="login" value="" /><br /> ${password_message}:<br />
		<input type="password" name="password" value="" /><br /> 
		<input type="submit" name="signInButton" onclick="signIn();" value="${sign_in_message}" /><br />
		<input type="submit" name="signUpButton" onclick="signUp();" value="${sign_up_message}" /><br />
		<input type="hidden" id="requestParameter" name="requestParameter" value="-1">
	</form>
	<c:set var = "answer" scope = "session" value = "${requestScope.answer}"/>
	<c:if test = "${answer != null}">
         <h3><c:out value="${answer}" /></h3>
    </c:if>
    
    	<form action="setLocale" method="post">
		<input type="hidden" name="requestParameter" value="ru" />
		<input type="submit" name="setRULocale" value="${locale_ru}" /><br />
		</form>
		<form action="setLocale" method="post">
		<input type="hidden" name="requestParameter" value="en" />
		<input type="submit" name="setENLocale" value="${locale_en}" /><br />
		</form>
	<script src="js/script.js"></script>
</body>
</html>