package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        int mood = Integer.parseInt(request.getParameter("mood"));
        String comment = request.getParameter("comment");

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO entries (user_id, date, mood, comment) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, mood);
            stmt.setString(4, comment);
            stmt.executeUpdate();

            response.sendRedirect("main.jsp?success=1");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("main.jsp?error=1");
        }
    }
}
