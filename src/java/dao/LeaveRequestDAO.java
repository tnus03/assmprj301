package dao;

import java.sql.*;
import java.util.*;
import java.util.Date;
import model.LeaveRequest;
import model.User;

public class LeaveRequestDAO extends DBContext {

    public void createRequest(LeaveRequest req) {
        String sql = "INSERT INTO Requests (user_id, from_date, to_date, reason, status_id, processed_by, created_date) " +
                     "VALUES (?, ?, ?, ?, ?, NULL, GETDATE())";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, req.getUserId());
            ps.setDate(2, new java.sql.Date(req.getFromDate().getTime()));
            ps.setDate(3, new java.sql.Date(req.getToDate().getTime()));
            ps.setString(4, req.getReason());
            ps.setInt(5, 1); // Inprogress

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public List<LeaveRequest> getRequestsForUser(User user) {
        List<LeaveRequest> list = new ArrayList<>();
        String sql;

        if ("admin".equalsIgnoreCase(user.getRole())) {
            sql = "SELECT r.*, u.name\n" +
                  "FROM Requests r\n" +
                  "JOIN Users u ON r.user_id = u.user_id";
        } else if ("manager".equalsIgnoreCase(user.getRole())) {
            sql = """
                  SELECT r.*, u.name
                  FROM Requests r
                  JOIN Users u ON r.user_id = u.user_id
                  WHERE r.user_id = ?
                  OR r.user_id IN (SELECT user_id FROM Users WHERE manager_id = ?)
                  """;
        } else {
            sql = "SELECT r.*, u.name\n" +
                   "FROM Requests r\n" +
                   "JOIN Users u ON r.user_id = u.user_id\n" +
                   "WHERE r.user_id = ?";
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if ("manager".equalsIgnoreCase(user.getRole())) {
                ps.setInt(1, user.getUserId());
                ps.setInt(2, user.getUserId());
            } else if (!"admin".equalsIgnoreCase(user.getRole())) {
                ps.setInt(1, user.getUserId());
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest r = new LeaveRequest();
                r.setRequestId(rs.getInt("request_id"));
                r.setUserId(rs.getInt("user_id")); 
                r.setCreatorName(rs.getString("name"));
                r.setFromDate(rs.getDate("from_date"));
                r.setToDate(rs.getDate("to_date"));
                r.setReason(rs.getString("reason"));
                r.setStatusId(rs.getInt("status_id"));
                r.setProcessedBy(rs.getObject("processed_by") != null ? rs.getInt("processed_by") : null);
                r.setCreatedDate(rs.getDate("created_date"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<LeaveRequest> getPendingRequestsForReviewer(User user) {
    List<LeaveRequest> list = new ArrayList<>();
    String sql;

    if ("admin".equalsIgnoreCase(user.getRole())) {
        sql = "SELECT r.*, u.name FROM Requests r JOIN Users u ON r.user_id = u.user_id WHERE r.status_id = 1";
    } else if ("manager".equalsIgnoreCase(user.getRole())) {
        sql = """
              SELECT r.*, u.name
              FROM Requests r
              JOIN Users u ON r.user_id = u.user_id
              WHERE r.status_id = 1 AND r.user_id IN (
                  SELECT user_id FROM Users WHERE manager_id = ?
              )
              """;
    } else {
        return list; // staff không có quyền
    }

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        if ("manager".equalsIgnoreCase(user.getRole())) {
            ps.setInt(1, user.getUserId());
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LeaveRequest r = new LeaveRequest();
            r.setRequestId(rs.getInt("request_id"));
            r.setUserId(rs.getInt("user_id"));
            r.setCreatorName(rs.getString("name")); // phải có trong model
            r.setFromDate(rs.getDate("from_date"));
            r.setToDate(rs.getDate("to_date"));
            r.setReason(rs.getString("reason"));
            r.setStatusId(rs.getInt("status_id"));
            r.setCreatedDate(rs.getDate("created_date"));
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
   
    
    public void updateStatus(int requestId, int statusId, int approvedBy) {
    String sql = "UPDATE Requests SET status_id = ?, processed_by = ? WHERE request_id = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, statusId);
        ps.setInt(2, approvedBy);
        ps.setInt(3, requestId);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
    public LeaveRequest getRequestById(int requestId) {
    String sql = "SELECT r.*, u.name FROM Requests r JOIN Users u ON r.user_id = u.user_id WHERE request_id = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, requestId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            LeaveRequest r = new LeaveRequest();
            r.setRequestId(rs.getInt("request_id"));
            r.setUserId(rs.getInt("user_id"));
            r.setCreatorName(rs.getString("name"));
            r.setFromDate(rs.getDate("from_date"));
            r.setToDate(rs.getDate("to_date"));
            r.setReason(rs.getString("reason"));
            r.setStatusId(rs.getInt("status_id"));
            r.setCreatedDate(rs.getDate("created_date"));
            return r;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    public void updateRequest(LeaveRequest r) {
    String sql = "UPDATE Requests SET from_date = ?, to_date = ?, reason = ? WHERE request_id = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setDate(1, new java.sql.Date(r.getFromDate().getTime()));
        ps.setDate(2, new java.sql.Date(r.getToDate().getTime()));
        ps.setString(3, r.getReason());
        ps.setInt(4, r.getRequestId());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public List<LeaveRequest> getAllApprovedRequests(Date from, Date to) {
    List<LeaveRequest> list = new ArrayList<>();
    String sql = """
        SELECT r.*, u.name, d.department_name
        FROM Requests r
        JOIN Users u ON r.user_id = u.user_id
        JOIN Departments d ON u.department_id = d.department_id
        WHERE r.status_id = 2
          AND (r.from_date <= ? AND r.to_date >= ?)
    """;

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDate(1, new java.sql.Date(to.getTime()));
        ps.setDate(2, new java.sql.Date(from.getTime()));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LeaveRequest r = new LeaveRequest();
            r.setUserId(rs.getInt("user_id"));
            r.setCreatorName(rs.getString("name"));
            r.setFromDate(rs.getDate("from_date"));
            r.setToDate(rs.getDate("to_date"));
            r.setDepartmentName(rs.getString("department_name")); // ⚠️ dùng để hiển thị phòng trong JSP
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    
    public List<LeaveRequest> getApprovedRequestsInRangeByDepartment(int departmentId, Date from, Date to) {
    List<LeaveRequest> list = new ArrayList<>();
    String sql = """
        SELECT r.*, u.name
        FROM Requests r
        JOIN Users u ON r.user_id = u.user_id
        WHERE r.status_id = 2
          AND u.department_id = ?
          AND (r.from_date <= ? AND r.to_date >= ?)
    """;

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, departmentId);
        ps.setDate(2, new java.sql.Date(to.getTime()));
        ps.setDate(3, new java.sql.Date(from.getTime()));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LeaveRequest r = new LeaveRequest();
            r.setUserId(rs.getInt("user_id"));
            r.setCreatorName(rs.getString("name"));
            r.setFromDate(rs.getDate("from_date"));
            r.setToDate(rs.getDate("to_date"));
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    public int countRequestsByStatus(String role, int userId, int departmentId, int status) {
    String sql;
    if ("staff".equalsIgnoreCase(role)) {
        sql = "SELECT COUNT(*) FROM Requests WHERE user_id = ? AND status_id = ?";
    } else if ("manager".equalsIgnoreCase(role)) {
        sql = """
            SELECT COUNT(*) FROM Requests r
            JOIN Users u ON r.user_id = u.user_id
            WHERE u.department_id = ? AND r.status_id = ?
        """;
    } else { // admin
        sql = "SELECT COUNT(*) FROM Requests WHERE status_id = ?";
    }

    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        if ("staff".equalsIgnoreCase(role)) {
            ps.setInt(1, userId);
            ps.setInt(2, status);
        } else if ("manager".equalsIgnoreCase(role)) {
            ps.setInt(1, departmentId);
            ps.setInt(2, status);
        } else {
            ps.setInt(1, status);
        }

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);

    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}


    
    
}
