<%@ page import="model.LeaveRequest" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    LeaveRequest r = (LeaveRequest) request.getAttribute("r");
%>

<html>
<head>
    <title>Sửa đơn nghỉ phép</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<h2>Sửa đơn nghỉ phép</h2>

<form method="post" action="${pageContext.request.contextPath}/request/modif">
    <input type="hidden" name="id" value="<%= r.getRequestId() %>">
    Từ ngày: <input type="date" name="from" value="<%= r.getFromDate() %>" required><br><br>
    Đến ngày: <input type="date" name="to" value="<%= r.getToDate() %>" required><br><br>
    Lý do: <br>
    <textarea name="reason" rows="4" cols="40" required><%= r.getReason() %></textarea><br><br>
    <input type="submit" value="Cập nhật">
</form>

<p style="color:red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
<a href="${pageContext.request.contextPath}/request/list">Danh sách đơn</a>
</body>
</html>
