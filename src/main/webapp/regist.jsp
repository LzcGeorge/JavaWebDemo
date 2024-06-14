
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
    <script type="text/javascript">
        function _change() {
            var ele = document.getElementById("verifyCode");
            ele.src = "/user/VerifyCodeServlet?xxx=" + new Date().getTime();

        }
    </script>
</head>
<body>
    <h1>regist</h1>
    <p style="color: red; font-weight: 900">${msg}</p>

    <form action="${pageContext.request.contextPath}/RegistServlet" method="post">
        username: <input type="text" name="username" value="${user.username}"/> <br>
        password: <input type="password" name="password" value="${user.password}"/> <br>
        verifycode: <input type="text" name="verifyCode" value="${user.verifyCode}" size="4">
                    <img id="verifyCode" src="/user/VerifyCodeServlet"/>
        <a href="javascript:_change()"/>change it </a><br>
        <input type="submit" value="register"/>
    </form>
</body>
</html>
