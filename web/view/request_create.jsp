<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tạo đơn nghỉ phép</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<h2>Tạo đơn nghỉ phép</h2>

<form action="${pageContext.request.contextPath}/request/creat" method="post">
    Từ ngày: <input type="date" name="from" required><br><br>
    Đến ngày: <input type="date" name="to" required><br><br>
    Lý do: <br>
    <textarea name="reason" rows="4" cols="40" required></textarea><br><br>
    <input type="submit" value="Gửi đơn">
</form>

<p style="color:red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>

<a href="${pageContext.request.contextPath}/home">Trang chủ</a>
</body>
</html>
