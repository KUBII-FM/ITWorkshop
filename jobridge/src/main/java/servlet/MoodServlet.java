package servlet;

import java.io.IOException;
import java.sql.Connection;
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

@WebServlet("/MoodServlet")
public class MoodServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // セッション確認
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("./index.jsp?error=3"); // セッション切れエラー
            return;
        }

        String userId = (String) session.getAttribute("user_id");
        String mood = request.getParameter("mood");
        String comment = request.getParameter("comment");
        LocalDate date = LocalDate.now();

        if (mood == null || mood.isEmpty()) {
            response.sendRedirect("UserMainServlet?error=missing_mood"); // 気持ちが未選択の場合のエラー
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO entries (user_id, mood, comment, date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setInt(2, Integer.parseInt(mood));
            stmt.setString(3, comment);
            stmt.setDate(4, java.sql.Date.valueOf(date));

            stmt.executeUpdate();
            response.sendRedirect("UserMainServlet?success=entry_added"); // 登録成功
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("UserMainServlet?error=database_error"); // データベースエラー
        }
    }
}
