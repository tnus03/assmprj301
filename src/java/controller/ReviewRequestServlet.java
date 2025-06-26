package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.LeaveRequest;
import model.User;

@WebServlet("/request/review")
public class ReviewRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (!user.getRole().equalsIgnoreCase("admin") &&
            !user.getRole().equalsIgnoreCase("manager")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập.");
            return;
        }

        List<LeaveRequest> list = new LeaveRequestDAO().getPendingRequestsForReviewer(user);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/view/request_review.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int requestId = Integer.parseInt(req.getParameter("requestId"));
        int statusId = Integer.parseInt(req.getParameter("statusId"));
        User user = (User) req.getSession().getAttribute("user");

        new LeaveRequestDAO().updateStatus(requestId, statusId, user.getUserId());

        resp.sendRedirect(req.getContextPath() + "/request/review");
    }
}
