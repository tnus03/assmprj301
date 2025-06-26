/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        LeaveRequestDAO dao = new LeaveRequestDAO();
        String role = user.getRole();

        int pending = dao.countRequestsByStatus(role, user.getUserId(), user.getDepartmentId(), 1);
        int approved = dao.countRequestsByStatus(role, user.getUserId(), user.getDepartmentId(), 2);
        int rejected = dao.countRequestsByStatus(role, user.getUserId(), user.getDepartmentId(), 3);

        req.setAttribute("pending", pending);
        req.setAttribute("approved", approved);
        req.setAttribute("rejected", rejected);

        req.getRequestDispatcher("/view/home.jsp").forward(req, resp);
    }
}
