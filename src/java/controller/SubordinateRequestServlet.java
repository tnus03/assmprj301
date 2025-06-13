/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

// =========================
// controller/SubordinateRequestServlet.java (FIX chính xác)
// =========================
package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.LeaveRequest;
import model.User;

@WebServlet("/request/subordinates")
public class SubordinateRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        LeaveRequestDAO dao = new LeaveRequestDAO();
        List<LeaveRequest> list;

        // Nếu là admin: lấy đơn của tất cả user có role != admin
        if (u.getRole().equalsIgnoreCase("admin")) {
            list = dao.getRequestsOfAllNonAdmins();
        }
        // Nếu là leader: lấy đơn của nhân viên cấp dưới
        else if (u.getRole().equalsIgnoreCase("leader")) {
            list = dao.getRequestsOfSubordinates(u.getUserId());
        }
        // Nếu là nhân viên: chuyển về trang của họ
        else {
            response.sendRedirect("home.jsp");
            return;
        }

        request.setAttribute("list", list);
        request.getRequestDispatcher("/view/subordinate_requests.jsp").forward(request, response);
    }
}