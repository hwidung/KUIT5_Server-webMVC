package controller;

import core.db.MemoryUserRepository;
import core.web.Controller;
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
public class UpdateUserFormController implements Controller {
    private static final String USER_SESSION_KEY = "user";
    @Override
    public String handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);

        if (sessionUser == null) {
            return "redirect:/user/login.jsp";
        }

        String userId = req.getParameter("userId");
        if (!sessionUser.isSameUser(userId)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "본인의 정보만 수정할 수 있습니다.");
            return null;
        }

        User user = MemoryUserRepository.getInstance().findUserById(userId);
        req.setAttribute("user", user);

        return "/WEB-INF/views/user/updateForm.jsp";
    }
}
