/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import model.LeaveRequest;
import model.User;

@WebServlet("/request/create")
public class CreateRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/request_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String reason = request.getParameter("reason");
        Date fromDate = Date.valueOf(request.getParameter("from_date"));
        Date toDate = Date.valueOf(request.getParameter("to_date"));

        LeaveRequest requestObj = new LeaveRequest(title, reason, fromDate, toDate, u.getUserId(), 1);

        LeaveRequestDAO dao = new LeaveRequestDAO();
        dao.createLeaveRequest(requestObj);

        // Log kiểm tra
        System.out.println("Đơn đã được tạo bởi user_id: " + u.getUserId());

        response.sendRedirect(request.getContextPath() + "/view/request_list.jsp");
    }
}
