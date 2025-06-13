/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.LeaveRequest;

public class LeaveRequestDAO extends DBContext {
    public void createLeaveRequest(LeaveRequest r) {
        String sql = "INSERT INTO LeaveRequests (title, reason, from_date, to_date, created_by, status_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getTitle());
            ps.setString(2, r.getReason());
            ps.setDate(3, r.getFromDate());
            ps.setDate(4, r.getToDate());
            ps.setInt(5, r.getCreatedBy());
            ps.setInt(6, r.getStatusId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<LeaveRequest> getRequestsByUserId(int userId) {
    List<LeaveRequest> list = new ArrayList<>();
    String sql = "SELECT * FROM LeaveRequests WHERE created_by = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LeaveRequest r = new LeaveRequest();
            r.setTitle(rs.getString("title"));
            r.setReason(rs.getString("reason"));
            r.setFromDate(rs.getDate("from_date"));
            r.setToDate(rs.getDate("to_date"));
            r.setStatusId(rs.getInt("status_id"));
            r.setCreatedBy(rs.getInt("created_by"));
            r.setRequestId(rs.getInt("request_id"));
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

public void updateRequestStatus(int requestId, int statusId) {
    String sql = "UPDATE LeaveRequests SET status_id = ? WHERE request_id = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, statusId);
        ps.setInt(2, requestId);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  public List<LeaveRequest> getRequestsOfSubordinates(int managerId) {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT r.* FROM LeaveRequests r " +
                     "JOIN Users u ON r.created_by = u.user_id " +
                     "WHERE u.manager_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest r = new LeaveRequest();
                r.setRequestId(rs.getInt("request_id"));
                r.setTitle(rs.getString("title"));
                r.setReason(rs.getString("reason"));
                r.setFromDate(rs.getDate("from_date"));
                r.setToDate(rs.getDate("to_date"));
                r.setCreatedBy(rs.getInt("created_by"));
                r.setStatusId(rs.getInt("status_id"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
public List<LeaveRequest> getRequestsByDepartmentId(int userId) {
    List<LeaveRequest> list = new ArrayList<>();
    String sql = "SELECT r.* " +
                 "FROM LeaveRequests r " +
                 "JOIN Users u ON r.created_by = u.user_id " +
                 "WHERE u.department_id = (SELECT department_id FROM Users WHERE user_id = ?)";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            LeaveRequest r = new LeaveRequest();
            r.setRequestId(rs.getInt("request_id"));
            r.setTitle(rs.getString("title"));
            r.setReason(rs.getString("reason"));
            r.setFromDate(rs.getDate("from_date"));
            r.setToDate(rs.getDate("to_date"));
            r.setCreatedBy(rs.getInt("created_by"));
            r.setStatusId(rs.getInt("status_id"));
            list.add(r);
        }
    } catch (Exception e) {
        System.err.println("Lỗi khi lấy danh sách đơn theo phòng ban:");
        e.printStackTrace();
    }
    return list;
}
public List<LeaveRequest> getRequestsOfAllNonAdmins() {
    List<LeaveRequest> list = new ArrayList<>();
    String sql = "SELECT r.* FROM LeaveRequests r " +
                 "JOIN Users u ON r.created_by = u.user_id " +
                 "WHERE u.role != 'admin'";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LeaveRequest r = new LeaveRequest();
            r.setRequestId(rs.getInt("request_id"));
            r.setTitle(rs.getString("title"));
            r.setReason(rs.getString("reason"));
            r.setFromDate(rs.getDate("from_date"));
            r.setToDate(rs.getDate("to_date"));
            r.setCreatedBy(rs.getInt("created_by"));
            r.setStatusId(rs.getInt("status_id"));
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    
}
