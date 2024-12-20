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

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // セッション確認
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("index.jsp?error=3"); // セッション切れエラー
            return;
        }

        String userId = (String) session.getAttribute("user_id");
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT mood, comment, date FROM entries WHERE user_id = ? AND date = CURDATE()";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("mood", rs.getInt("mood"));
                request.setAttribute("comment", rs.getString("comment"));
                request.setAttribute("date", rs.getDate("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "database_error");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_main.jsp");
        dispatcher.forward(request, response);
    }
}