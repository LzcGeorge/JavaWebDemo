<%--
  Created by IntelliJ IDEA.
  User: selfknow
  Date: 2024/6/23
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .centered {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            font-size: 1.5em; /* 放大字体 */
        }

        .form-container {
            margin: 60px;
        }
    </style>
</head>
<body>
    <h1 style="align-content: baseline">Jave Web</h1>
    <p>
        <a href="${pageContext.request.contextPath}/user/regist.jsp">Go to User</a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/customer">Go to Customer</a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/bookstore">Go to Bookstore</a>
    </p>
</body>
</html>
