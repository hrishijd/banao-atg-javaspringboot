<%@page import="com.authentication.loginsystem.models.Users"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%! int i; %>
    <%! ArrayList<Users> ar; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>UserTable</h1>
<table>
<% ar=(ArrayList)request.getAttribute("users"); %>
		<tr>      
            <th>Name</th>
            <th>Email</th>
            <th>isAdmin</th>
            <th>isVerified</th>
        </tr>
<% for(i = 0; i < ar.size(); i++) { %>
        <tr>      
        
            <td><%=ar.get(i).getName()%></td>
            <td><%=ar.get(i).getEmail()%></td>
            <td><%=ar.get(i).isAdmin()%></td>
            <td><%=ar.get(i).isVerified()%></td>
        </tr>
    <% } %>
</table>
<form action="logout">
<input type="submit" value="Logout">
</form>
</body>
</html>