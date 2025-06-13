<%-- 
    Document   : login
    Created on : Jun 13, 2025, 2:20:12 PM
    Author     : ACER
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <title>Đăng nhập</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <h2>Đăng nhập hệ thống</h2>
        <p style="color:red">${requestScope.error}</p>
        Tên đăng nhập: <input type="text" name="username" required /><br/>
        Mật khẩu: <input type="password" name="password" required /><br/>
        <input type="submit" value="Đăng nhập" />
    </form>
</body>
</html>

