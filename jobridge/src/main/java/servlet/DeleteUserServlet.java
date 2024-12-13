package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseUtil;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");

        if (userId == null || userId.isEmpty()) {
            response.sendRedirect("AdminServlet?error=missing_user_id");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                response.sendRedirect("AdminServlet?success=user_deleted");
            } else {
                response.sendRedirect("AdminServlet?error=user_not_found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("AdminServlet?error=database_error");
        }
    }
}
