package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.LeaveRequest;
import model.User;

@WebServlet("/request/creat")
public class CreateRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.getRequestDispatcher("/view/request_create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        try {
            User user = (User) req.getSession().getAttribute("user");

            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            // Chỉ cho phép staff & manager
            if (!user.getRole().equalsIgnoreCase("staff") &&
                !user.getRole().equalsIgnoreCase("manager")) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền tạo đơn.");
                return;
            }

            String from = req.getParameter("from");
            String to = req.getParameter("to");
            String reason = req.getParameter("reason");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            LeaveRequest lr = new LeaveRequest();
            lr.setUserId(user.getUserId());
            lr.setFromDate(sdf.parse(from));
            lr.setToDate(sdf.parse(to));
            lr.setReason(reason);
            lr.setStatusId(1); // Inprogress

            new LeaveRequestDAO().createRequest(lr);
            resp.sendRedirect(req.getContextPath() + "/request/list");
        } catch (IOException | ParseException e) {
            req.setAttribute("error", "Lỗi dữ liệu. Vui lòng thử lại.");
            req.getRequestDispatcher("/view/request_create.jsp").forward(req, resp);
        }
    }
}
