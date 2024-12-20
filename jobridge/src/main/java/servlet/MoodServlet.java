package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            response.sendRedirect("index.jsp?error=3"); // セッション切れエラー
            return;
        }

        String userId = (String) session.getAttribute("user_id");
        String mood = request.getParameter("mood");
        String comment = request.getParameter("comment");
        LocalDate date = LocalDate.now();

        if (mood == null || mood.isEmpty()) {
            response.sendRedirect("UserServlet?error=missing_mood"); // 気持ちが未選択の場合のエラー
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            // 既存データ確認
            String checkSql = "SELECT COUNT(*) FROM entries WHERE user_id = ? AND date = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, userId);
            checkStmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            boolean dataExists = rs.getInt(1) > 0;

            // データの挿入または更新
            if (dataExists) {
                String updateSql = "UPDATE entries SET mood = ?, comment = ? WHERE user_id = ? AND date = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, Integer.parseInt(mood));
                updateStmt.setString(2, comment);
                updateStmt.setString(3, userId);
                updateStmt.setDate(4, java.sql.Date.valueOf(date));
                updateStmt.executeUpdate();
//                System.out.println("Updated entry for user_id: " + userId);
            } else {
                String insertSql = "INSERT INTO entries (user_id, mood, comment, date) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, userId);
                insertStmt.setInt(2, Integer.parseInt(mood));
                insertStmt.setString(3, comment);
                insertStmt.setDate(4, java.sql.Date.valueOf(date));
                insertStmt.executeUpdate();
//                System.out.println("Inserted new entry for user_id: " + userId);
            }

            response.sendRedirect("UserServlet?success=entry_added"); // 登録または更新成功
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("UserServlet?error=database_error"); // データベースエラー
        }
    }
}
