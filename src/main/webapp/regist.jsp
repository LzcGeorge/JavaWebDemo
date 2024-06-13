<%--
  Created by IntelliJ IDEA.
  User: selfknow
  Date: 2024/6/13
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>regist</h1>

    <form action="${pageContext.request.contextPath}/RegistServlet" method="post">
        username: <input type="text" name="username "/>
        password: <input type="password" name="password"/>
        <input type="submit" value="register"/>
    </form>
</body>
</html>
