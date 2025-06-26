<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="model.LeaveRequest" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    List<LeaveRequest> requests = (List<LeaveRequest>) request.getAttribute("requests");
    String from = (String) request.getAttribute("from");
    String to = (String) request.getAttribute("to");
    String role = (String) request.getAttribute("role");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date fromDate = sdf.parse(from);
    Date toDate = sdf.parse(to);

    Calendar cal = Calendar.getInstance();
    Map<String, Set<String>> leaveMap = new LinkedHashMap<>();

    for (LeaveRequest r : requests) {
        String name = r.getCreatorName();
        if ("admin".equalsIgnoreCase(role) && r.getDepartmentName() != null) {
            name += " (" + r.getDepartmentName() + ")";
        }

        cal.setTime(r.getFromDate());
        while (!cal.getTime().after(r.getToDate())) {
            String d = sdf.format(cal.getTime());
            leaveMap.computeIfAbsent(name, k -> new HashSet<>()).add(d);
            cal.add(Calendar.DATE, 1);
        }
    }

    List<String> days = new ArrayList<>();
    cal.setTime(fromDate);
    while (!cal.getTime().after(toDate)) {
        days.add(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, 1);
    }
%>

<html>
<head>
    <title>Lịch nghỉ phòng ban</title>
    
    <style>
        table { border-collapse: collapse; }
        th, td { padding: 5px 10px; text-align: center; border: 1px solid black; }
        .off { background-color: #ffcccc; }
        .on { background-color: #ccffcc; }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>

<h2>Lịch nghỉ từ <%= from %> đến <%= to %></h2>

<form method="get" action="agenda">
    Từ ngày: <input type="date" name="from" value="<%= from %>">
    Đến ngày: <input type="date" name="to" value="<%= to %>">
    <button type="submit">Xem</button>
</form>

<table>
    <tr>
        <th>Nhân viên</th>
        <% for (String d : days) { %>
            <th><%= d %></th>
        <% } %>
    </tr>
    <% for (String name : leaveMap.keySet()) { %>
        <tr>
            <td><%= name %></td>
            <% for (String d : days) { %>
                <% boolean isOff = leaveMap.get(name).contains(d); %>
                <td class="<%= isOff ? "off" : "on" %>"><%= isOff ? "Nghỉ" : "Làm" %></td>
            <% } %>
        </tr>
    <% } %>
</table>

<br>
<a href="${pageContext.request.contextPath}/home">🏠 Trang chủ</a>

</body>
</html>
