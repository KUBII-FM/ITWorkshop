package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DatabaseUtil;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "password_mismatch");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String checkSql = "SELECT COUNT(*) FROM users WHERE user_id = ? OR username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, userId);
            checkStmt.setString(2, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                request.setAttribute("error", "user_or_username_exists");
                RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/register.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String insertSql = "INSERT INTO users (user_id, username, password) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, userId);
            insertStmt.setString(2, username);
            insertStmt.setString(3, password);
            insertStmt.executeUpdate();

            // セッション無効化（ログアウト処理）
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // index.jspにリダイレクトし、成功メッセージを表示
            response.sendRedirect("index.jsp?success=registered");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "database_error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/register.jsp");
            dispatcher.forward(request, response);
        }
    }
}
