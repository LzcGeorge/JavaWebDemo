<%--
  Created by IntelliJ IDEA.
  User: selfknow
  Date: 2024/6/13
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<h1>login</h1>
<p style="color: red; font-weight: 900">${msg}</p>

<form action="${pageContext.request.contextPath}/LoginServlet" method="post">
    username: <input type="text" name="username" value="${user.username}"/> <br>
    password: <input type="password" name="password" value="${user.password}"/> <br>
    <input type="submit" value="login"/>
</form>
</body>
</html>
