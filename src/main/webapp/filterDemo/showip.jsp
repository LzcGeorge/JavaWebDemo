<%--
  Created by IntelliJ IDEA.
  User: selfknow
  Date: 2024/6/27
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>showip</title>
</head>
<body>
<h1  style="margin-left: auto;margin-right: auto;width: 10%">show ip</h1>
<table border="1px" style="margin-left: auto;margin-right: auto;width: 50%">
    <tr>
        <th>IP</th>
        <th>次数</th>
    </tr>
<c:forEach items="${map}" var="var_map">
    <tr>
        <td>${var_map.key}</td>
        <td>${var_map.value}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
