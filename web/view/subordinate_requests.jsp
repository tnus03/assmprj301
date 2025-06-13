<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <title>Quản lý đơn cấp dưới</title>
</head>
<body>
<h2>Danh sách đơn của cấp dưới</h2>
<c:if test="${empty list}">
    <p style="color: red">Không có đơn nào của cấp dưới.</p>
</c:if>
<c:if test="${not empty list}">
<table border="1">
    <tr>
        <th>Tiêu đề</th>
        <th>Người tạo</th>
        <th>Từ ngày</th>
        <th>Đến ngày</th>
        <th>Lý do</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
    </tr>
    <c:forEach var="r" items="${list}">
        <tr>
            <td><c:out value="${r.title}"/></td>
            <td>${r.createdBy}</td>
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
            <td>
                <a href="${pageContext.request.contextPath}/request/approve?id=${r.requestId}">Duyệt</a> |
                <a href="${pageContext.request.contextPath}/request/reject?id=${r.requestId}">Từ chối</a>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>
<br/>
<a href="${pageContext.request.contextPath}/view/home.jsp">Trang chủ</a>
</body>
</html>
