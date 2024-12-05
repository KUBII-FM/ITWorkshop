package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseUtil;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        // パスワード確認
        if (!password.equals(confirmPassword)) {
            response.sendRedirect("jsp/register.jsp?error=password_mismatch");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            // 重複チェック
            String checkSql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, userId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                response.sendRedirect("jsp/register.jsp?error=user_exists");
                return;
            }

            // 新規登録
            String insertSql = "INSERT INTO users (user_id, username, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSql);
            stmt.setString(1, userId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();

            response.sendRedirect("jsp/index.jsp?success=registered");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("jsp/register.jsp?error=database_error");
        }
    }
}
