package dao;

import java.sql.*;
import model.User;

public class UserDAO extends DBContext {

    public User login(String username, String password) {
        String sql = """
            SELECT u.user_id, u.username, u.password, u.name, u.department_id, u.manager_id, r.role_name
            FROM Users u
            JOIN UserRole ur ON u.user_id = ur.user_id
            JOIN Roles r ON ur.role_id = r.role_id
            WHERE u.username = ? AND u.password = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setName(rs.getString("name"));
                u.setDepartmentId(rs.getInt("department_id"));
                u.setManagerId(rs.getObject("manager_id") != null ? rs.getInt("manager_id") : null);
                u.setRole(rs.getString("role_name"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
