<%-- 
    Document   : request_create
    Created on : Jun 13, 2025, 2:49:47 PM
    Author     : ACER
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <title>Tạo đơn xin nghỉ phép</title>
</head>
<body>
    <h2>Tạo đơn xin nghỉ phép</h2>
    <form action="${pageContext.request.contextPath}/request/create" method="post">
        Tiêu đề: <input type="text" name="title" required /><br/>
        Từ ngày: <input type="date" name="from_date" required /><br/>
        Đến ngày: <input type="date" name="to_date" required /><br/>
        Lý do: <br/>
        <textarea name="reason" rows="5" cols="40" required></textarea><br/>
        <input type="submit" value="Gửi đơn" />
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/view/home.jsp">Trang chủ</a>
</body>
</html>

