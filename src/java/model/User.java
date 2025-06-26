package model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String name;
    private int departmentId;
    private Integer managerId;
    private String role; // role_name

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFullName() {
    return name; 
}
}
