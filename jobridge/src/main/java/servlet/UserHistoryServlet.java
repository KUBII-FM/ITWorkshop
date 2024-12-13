package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseUtil;

@WebServlet("/UserHistoryServlet")
public class UserHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        // 必須パラメータのチェック
        if (userId == null || userId.isEmpty()) {
            request.setAttribute("error", "利用者IDが指定されていません。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // デフォルト期間設定
        LocalDate defaultEndDate = LocalDate.now();
        LocalDate defaultStartDate = defaultEndDate.minusDays(30);

        // 開始日と終了日のバリデーション
        if (startDate == null || startDate.isEmpty()) {
            startDate = defaultStartDate.toString();
        }
        if (endDate == null || endDate.isEmpty()) {
            endDate = defaultEndDate.toString();
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT date, mood, comment FROM entries " +
                         "WHERE user_id = ? AND date BETWEEN ? AND ? " +
                         "ORDER BY date ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            stmt.setDate(3, java.sql.Date.valueOf(endDate));

            ResultSet rs = stmt.executeQuery();
            ArrayList<HashMap<String, String>> commentData = new ArrayList<>();

            while (rs.next()) {
                HashMap<String, String> record = new HashMap<>();
                record.put("date", rs.getDate("date").toString());
                record.put("mood", String.valueOf(rs.getInt("mood")));
                record.put("comment", rs.getString("comment") != null ? rs.getString("comment") : "なし");
                commentData.add(record);
            }

            // リクエスト属性にデータをセット
            request.setAttribute("commentData", commentData);
            request.setAttribute("userId", userId);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "データベースエラーが発生しました。");
        }

        // JSP にフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user_history.jsp");
        dispatcher.forward(request, response);
    }
}
