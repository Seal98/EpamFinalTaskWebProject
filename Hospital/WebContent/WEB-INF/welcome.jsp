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
		<input type="submit" onclick="userLogOut();" id="backToMainPageButton"
			name="backToMainPageButton" value="Back" /><br /> <input
			type="hidden" id="requestParameter" name="requestParameter"
			value="-1">
	</form>
	<div>
		Welcome, <c:out value="${requestScope.login}"/> <c:out value="${requestScope.type}"/>
	</div>
	<script>
		function userLogOut() {
			document.getElementById('requestParameter').value = "LOG_OUT";
		}
	</script>
</body>
</html>