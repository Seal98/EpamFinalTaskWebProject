<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="createUser" method="post">
		<input type="submit" onclick="userLogOut();" id="backToMainPageButton" name="backToMainPageButton" value="Back" /><br />
		<input type="hidden" id="requestParameter" name="requestParameter" value="-1">
	</form>
	<%
		if (request.getAttribute("login") != null && request.getAttribute("userType") != null) {
			out.println("<h3> Welcome, " + (String) request.getAttribute("userType") + " " + (String) request.getAttribute("login") + "</h3>");
		}
	%>
	<script>
	function userLogOut(){
		document.getElementById('requestParameter').value = "LOG_OUT";
	}
	</script>
</body>
</html>