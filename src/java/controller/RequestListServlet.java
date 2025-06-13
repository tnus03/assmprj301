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
import java.util.List;
import model.LeaveRequest;
import model.User;

@WebServlet("/request/list")
public class RequestListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        LeaveRequestDAO dao = new LeaveRequestDAO();
        List<LeaveRequest> list = dao.getRequestsByUserId(u.getUserId());

        request.setAttribute("list", list);
        request.getRequestDispatcher("/view/request_list.jsp").forward(request, response);
    }
}
