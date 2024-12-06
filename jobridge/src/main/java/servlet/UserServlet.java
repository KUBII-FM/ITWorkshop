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
            response.sendRedirect("./index.jsp?error=3"); // セッション切れエラー
            return;
        }

        String userId = (String) session.getAttribute("user_id");
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT mood, comment, date FROM entries WHERE user_id = ? AND date = CURDATE()";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // データが存在する場合の処理
                int mood = rs.getInt("mood");
                String comment = rs.getString("comment");
                java.sql.Date date = rs.getDate("date");

                // JSP に渡すデータをリクエストスコープに設定
                request.setAttribute("mood", mood);
                request.setAttribute("comment", comment);
                request.setAttribute("date", date);
            } else {
                // データが存在しない場合
                System.out.println("No data found for user_id: " + userId + " on date: " + java.time.LocalDate.now());
            }
        } catch (SQLException e) {
            // データベースエラー処理
            e.printStackTrace();
            request.setAttribute("error", "database_error");
        }

        // JSP にフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_main.jsp");
        dispatcher.forward(request, response);
    }
}