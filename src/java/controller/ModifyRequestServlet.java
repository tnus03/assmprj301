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

@WebServlet("/request/modif")
public class ModifyRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");

        // Kiểm tra id hợp lệ
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/request/list");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
            return;
        }

        LeaveRequestDAO dao = new LeaveRequestDAO();
        LeaveRequest r = dao.getRequestById(id);

        User user = (User) req.getSession().getAttribute("user");

        // Chỉ chủ đơn được sửa và chỉ khi trạng thái là đang xử lý
        if (r == null || user == null || r.getUserId() != user.getUserId() || r.getStatusId() != 1) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không được sửa đơn này");
            return;
        }

        req.setAttribute("r", r);
        req.getRequestDispatcher("/view/request_modify.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int requestId = Integer.parseInt(req.getParameter("id"));
            String from = req.getParameter("from");
            String to = req.getParameter("to");
            String reason = req.getParameter("reason");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            LeaveRequest r = new LeaveRequest();
            r.setRequestId(requestId);
            r.setFromDate(sdf.parse(from));
            r.setToDate(sdf.parse(to));
            r.setReason(reason);

            new LeaveRequestDAO().updateRequest(r);
            resp.sendRedirect(req.getContextPath() + "/request/list");

        } catch (NumberFormatException | ParseException e) {
            req.setAttribute("error", "Dữ liệu không hợp lệ hoặc lỗi định dạng ngày.");
            req.getRequestDispatcher("/view/request_modify.jsp").forward(req, resp);
        } catch (Exception ex) {
            req.setAttribute("error", "Lỗi cập nhật đơn.");
            req.getRequestDispatcher("/view/request_modify.jsp").forward(req, resp);
        }
    }
}
