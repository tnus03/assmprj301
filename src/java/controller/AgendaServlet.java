package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.LeaveRequest;
import model.User;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String fromStr = req.getParameter("from");
        String toStr = req.getParameter("to");

        if (fromStr == null || toStr == null) {
            fromStr = "2025-06-01";
            toStr = "2025-06-10";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date from = sdf.parse(fromStr);
            Date to = sdf.parse(toStr);

            LeaveRequestDAO dao = new LeaveRequestDAO();
            List<LeaveRequest> list;

            if ("admin".equalsIgnoreCase(user.getRole())) {
                list = dao.getAllApprovedRequests(from, to);
            } else {
                list = dao.getApprovedRequestsInRangeByDepartment(user.getDepartmentId(), from, to);
            }

            req.setAttribute("requests", list);
            req.setAttribute("from", fromStr);
            req.setAttribute("to", toStr);
            req.setAttribute("role", user.getRole());

            req.getRequestDispatcher("/view/agenda.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/view/agenda.jsp").forward(req, resp);
        }
    }
}