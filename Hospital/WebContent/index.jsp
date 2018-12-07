<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>MyJSP</title>
</head>
<body>
	<form action="authorization" method="post">
		<input type="hidden" name="command" value="forward" /> Enter login:<br />
		<input type="text" name="login" value="" /><br /> Enter password:<br />
		<input type="password" name="password" value="" /><br /> 
		<input type="submit" name="signInButton" onclick="signIn();" value="Sign in" /><br />
		<input type="submit" name="signUpButton" onclick="signUp();" value="Sign up" /><br />
		<input type="hidden" id="requestParameter" name="requestParameter" value="-1">
	</form>
	<%
		if (request.getAttribute("answer") != null) {
			out.println("<h3>" + (String) request.getAttribute("answer") + "</h3>");
		}
	%>
	<script src="js/script.js"></script>
</body>
</html>