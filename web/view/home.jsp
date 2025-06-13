<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
        return;
    }
%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
     <title>Trang chủ</title>
</head>
<body>
<h2>Chào mừng <%= u.getFullName() %> - Vai trò: <%= u.getRole() %></h2>

<% if (u.getRole().equals("admin")) { %>
    <h3>Quản lý hệ thống (Admin)</h3>
    <ul>
        <li><a href="<%= request.getContextPath() %>/request/subordinates">📋 Quản lý tất cả đơn nghỉ (Leader + Nhân viên)</a></li>
        <li><a href="<%= request.getContextPath() %>/agenda">📅 Xem lịch nghỉ toàn phòng</a></li>
    </ul>
<% } else if (u.getRole().equals("leader")) { %>
    <h3>Chức năng Trưởng nhóm</h3>
    <ul>
        <li><a href="<%= request.getContextPath() %>/request/subordinates">📋 Duyệt đơn của cấp dưới</a></li>
        <li><a href="<%= request.getContextPath() %>/agenda">📅 Xem lịch phòng ban</a></li>
        <li><a href="<%= request.getContextPath() %>/request/create">📝 Tạo đơn nghỉ phép</a></li>
        <li><a href="<%= request.getContextPath() %>/request/list">📄 Xem đơn của tôi</a></li>
    </ul>
<% } else if (u.getRole().equals("staff")) { %>
    <h3>Chức năng Nhân viên</h3>
    <ul>
        <li><a href="<%= request.getContextPath() %>/request/create">📝 Tạo đơn nghỉ phép</a></li>
        <li><a href="<%= request.getContextPath() %>/request/list">📄 Xem đơn của tôi</a></li>
    </ul>
<% } %>
<br/>
<a href="<%= request.getContextPath() %>/view/login.jsp">🔒 Đăng xuất</a>
</body>
</html>