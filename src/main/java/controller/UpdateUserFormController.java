package controller;

import core.db.MemoryUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jwp.model.User;

import java.io.IOException;

@WebServlet("/user/updateForm")
public class UpdateUserFormController extends HttpServlet {
    private static final String USER_SESSION_KEY = "user";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
        if (sessionUser == null) {
            resp.sendRedirect("/user/login.jsp");
            return;
        }
        String userId = req.getParameter("userId");
        if (!sessionUser.isSameUser(userId)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "본인의 정보만 수정할 수 있습니다."); // 403 에러 반환
            return;
        }
        User user = MemoryUserRepository.getInstance().findUserById(userId);
        req.setAttribute("user", user);

        RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
        rd.forward(req, resp);
    }
}
