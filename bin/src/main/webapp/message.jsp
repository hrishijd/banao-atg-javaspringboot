<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>
<%= request.getAttribute("message").toString()%>
</h3>
<form action="login">
<input type="submit" value="go to login">
</form>
</body>
</html>