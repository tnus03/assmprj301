<%-- 
    Document   : agenda
    Created on : Jun 13, 2025, 3:10:35 PM
    Author     : ACER
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.LeaveRequest" %>
<%@ page import="model.User" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
        return;
    }

    List<LeaveRequest> requests = (List<LeaveRequest>) request.getAttribute("requests");
    Map<String, boolean[]> agendaMap = new HashMap<>();

    for (LeaveRequest r : requests) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(r.getFromDate());
        int from = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(r.getToDate());
        int to = cal.get(Calendar.DAY_OF_MONTH);

        String name = "User_" + r.getCreatedBy();
        if (!agendaMap.containsKey(name)) agendaMap.put(name, new boolean[31]);
        for (int i = from - 1; i < to; i++) {
            agendaMap.get(name)[i] = true;
        }
    }
%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<title>Agenda phòng ban</title></head>
<body>
<h2>Lịch nghỉ của nhân sự trong phòng (tháng này)</h2>
<table border="1">
    <tr>
        <th>Nhân sự</th>
        <% for (int i = 1; i <= 31; i++) { %>
            <th><%= i %></th>
        <% } %>
    </tr>
    <% for (String user : agendaMap.keySet()) { %>
    <tr>
        <td><%= user %></td>
        <% boolean[] days = agendaMap.get(user); %>
        <% for (int i = 0; i < 31; i++) { %>
            <% if (days[i]) { %>
                <td style="background-color:red;"></td>
            <% } else { %>
                <td style="background-color:lightgreen;"></td>
            <% } %>
        <% } %>
    </tr>
    <% } %>
</table>
<a href="${pageContext.request.contextPath}/view/home.jsp">Trang chủ</a>
</body>
</html>
