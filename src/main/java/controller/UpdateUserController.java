package controller;

import core.db.MemoryUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwp.model.User;

import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        User existingUser = MemoryUserRepository.getInstance().findUserById(userId);
        if (existingUser == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        User updateUser = new User(userId, password, name, email);
        MemoryUserRepository.getInstance().changeUserInfo(updateUser);

        resp.sendRedirect("/user/userList");
    }

}
