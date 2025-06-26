<%@ page import="java.util.List" %>
<%@ page import="model.LeaveRequest" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    List<LeaveRequest> list = (List<LeaveRequest>) request.getAttribute("list");
%>

<html>
<head>
    <title>Xét duyệt đơn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<h2>Danh sách đơn cần xét duyệt</h2>

<table border="1" cellpadding="6">
    <tr>
        <th>STT</th>
        <th>Người tạo</th>
        <th>Từ ngày</th>
        <th>Đến ngày</th>
        <th>Lý do</th>
        <th>Hành động</th>
    </tr>
<%
    int i = 1;
    for (LeaveRequest r : list) {
%>
    <tr>
        <td><%= i++ %></td>
        <td><%= r.getCreatorName() %></td>
        <td><%= r.getFromDate() %></td>
        <td><%= r.getToDate() %></td>
        <td><%= r.getReason() %></td>
        <td>
            <form method="post" action="${pageContext.request.contextPath}/request/review">
                <input type="hidden" name="requestId" value="<%= r.getRequestId() %>">
                <button name="statusId" value="2">✔️ Duyệt</button>
                <button name="statusId" value="3">❌ Từ chối</button>
            </form>
        </td>
    </tr>
<% } %>
</table>

<br>
<a href="${pageContext.request.contextPath}/home">Trang chủ</a>
</body>
</html>
