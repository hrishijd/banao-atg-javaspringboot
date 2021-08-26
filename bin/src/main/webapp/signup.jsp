<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Signup form</h1>
	${SPRING_SECURITY_LAST_EXCEPTION.message}
	<form action="createuser" method="POST"><br>
	<h3>Name:</h3><input type="text" name="name"><br>
	<h3>E-mail:</h3><input type="text" name="email"><br>
	<h4>Password:</h4><input type="password" name="password"><br>
	<h4>Re-enter Password:</h4><input type="password" name="repassword"><br>
	<input name="submit" type="submit" value="submit">
</form>
</body>
</html>