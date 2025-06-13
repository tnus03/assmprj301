/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.LeaveRequest;
import model.User;
import java.io.IOException;
import java.util.List;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User u = (User) request.getSession().getAttribute("user");
        if (u == null) {
            response.sendRedirect("view/login.jsp");
            return;
        }
        LeaveRequestDAO dao = new LeaveRequestDAO();
        List<LeaveRequest> list = dao.getRequestsByDepartmentId(u.getUserId());
        request.setAttribute("requests", list);
        request.getRequestDispatcher("/view/agenda.jsp").forward(request, response);
    }
}

