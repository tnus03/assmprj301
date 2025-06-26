<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String role = user.getRole();
    int pending = (int) request.getAttribute("pending");
    int approved = (int) request.getAttribute("approved");
    int rejected = (int) request.getAttribute("rejected");
%>

<html>
<head>
    
    <title>Trang chủ</title>
    <style>
        body {
            font-family: Arial;
            margin: 40px;
        }
        h2 {
            color: #2e6da4;
        }
        ul {
            list-style-type: none;
            padding-left: 0;
        }
        li {
            margin: 8px 0;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .section {
            margin-bottom: 30px;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>

<div class="section">
    <h2>👋 Chào mừng, <%= user.getFullName() %>!</h2>
    <p>Vai trò của bạn: <strong><%= role.toUpperCase() %></strong></p>
</div>

<div class="section">
    <h3>📌 Chức năng</h3>
    <ul>
        <% if ("staff".equalsIgnoreCase(role) || "manager".equalsIgnoreCase(role)) { %>
            <li>🟢 <a href="<%= request.getContextPath() %>/request/creat">Tạo đơn nghỉ phép</a></li>
            <li>🟡 <a href="<%= request.getContextPath() %>/request/modif">Sửa đơn nghỉ</a></li>
        <% } %>

        <% if ("manager".equalsIgnoreCase(role) || "admin".equalsIgnoreCase(role)) { %>
            <li>🔵 <a href="<%= request.getContextPath() %>/request/review">Xét duyệt đơn</a></li>
            <li>📆 <a href="<%= request.getContextPath() %>/agenda">Lịch nghỉ phòng ban</a></li>
        <% } %>

        <li>📄 <a href="<%= request.getContextPath() %>/request/list">Xem danh sách đơn nghỉ phép</a></li>
    </ul>
</div>

<div class="section">
    <h3>📊 Thống kê đơn nghỉ phép</h3>
    <ul>
        <li>🔄 Đơn đang chờ xử lý: <strong><%= pending %></strong></li>
        <li>✅ Đơn đã duyệt: <strong><%= approved %></strong></li>
        <li>❌ Đơn bị từ chối: <strong><%= rejected %></strong></li>
    </ul>
</div>

<div class="section">
    <a href="<%= request.getContextPath() %>/logout">🔓 Đăng xuất</a>
</div>

</body>
</html>
