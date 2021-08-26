<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Login form</h1>
${SPRING_SECURITY_LAST_EXCEPTION.message}
<form action="login" method="post"><br>
<h3>Name:</h3><input type="text" name="username"><br>
<h4>Password:</h4><input type="password" name="password"><br>
<input name="submit" type="submit" value="submit">
</form>
<form action="signup" method="post">
<input name="submit" type="submit" value="Signup/Register">
</form>
</body>
</html>