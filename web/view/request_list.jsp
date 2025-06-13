<%-- 
    Document   : request_list
    Created on : Jun 13, 2025, 2:59:15 PM
    Author     : ACER
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.LeaveRequest" %>
<%@ page import="dao.LeaveRequestDAO" %>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
        return;
    }

    LeaveRequestDAO dao = new LeaveRequestDAO();
    List<LeaveRequest> list = dao.getRequestsByUserId(u.getUserId());
    request.setAttribute("list", list);
%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <title>Danh sách đơn nghỉ phép</title>
</head>
<body>
<h2>Danh sách đơn nghỉ phép của bạn</h2>
<table border="1">
    <tr>
        <th>Tiêu đề</th>
        <th>Từ ngày</th>
        <th>Đến ngày</th>
        <th>Lý do</th>
        <th>Trạng thái</th>
    </tr>
    <c:forEach var="r" items="${list}">
        <tr>
            <td><c:out value="${r.title}"/></td>
            <td><fmt:formatDate value="${r.fromDate}" pattern="dd-MM-yyyy"/></td>
            <td><fmt:formatDate value="${r.toDate}" pattern="dd-MM-yyyy"/></td>
            <td><c:out value="${r.reason}"/></td>
            <td>
                <c:choose>
                    <c:when test="${r.statusId == 1}">Đang xử lý</c:when>
                    <c:when test="${r.statusId == 2}">Đã duyệt</c:when>
                    <c:when test="${r.statusId == 3}">Từ chối</c:when>
                    <c:otherwise>Không xác định</c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="${pageContext.request.contextPath}/view/home.jsp">Trang chủ</a>
</body>
</html>