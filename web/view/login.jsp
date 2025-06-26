<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Đăng nhập</title>
    <style>
        body { font-family: Arial; text-align: center; padding-top: 100px; }
        input { margin: 5px; }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
    <h2>Đăng nhập hệ thống</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" name="username" placeholder="Tên đăng nhập" required><br>
        <input type="password" name="password" placeholder="Mật khẩu" required><br>
        <input type="submit" value="Đăng nhập">
    </form>

    <p style="color:red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
</body>
</html>
