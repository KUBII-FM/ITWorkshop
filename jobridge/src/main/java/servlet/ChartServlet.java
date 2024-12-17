package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson; // Gson を使用してデータを JSON に変換

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseUtil;

@WebServlet("/ChartServlet")
public class ChartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // リクエストパラメータから user_id を取得
        String userId = request.getParameter("user_id");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        if (userId == null || userId.isEmpty()) {
            // user_id が不足している場合はエラーページを表示
            request.setAttribute("error", "missing_user_id");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/admin.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // デフォルト期間設定
        if (startDate == null || startDate.isEmpty()) {
            startDate = LocalDate.now().minusDays(30).toString(); // 過去30日をデフォルト範囲に設定
        }
        if (endDate == null || endDate.isEmpty()) {
            endDate = LocalDate.now().toString(); // 現在の日付を終了日とする
        }
        
        // 日付のバリデーション
        if ((startDate == null || startDate.isEmpty()) || (endDate == null || endDate.isEmpty())) {
            request.setAttribute("error", "invalid_date_range");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/admin.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // データベースから気分データを取得
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT date, mood FROM entries WHERE user_id = ? AND date BETWEEN ? AND ? ORDER BY date ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

            ResultSet rs = stmt.executeQuery();
            ArrayList<HashMap<String, Object>> moodData = new ArrayList<>();

            // 結果セットからデータを取得し、リストに格納
            while (rs.next()) {
                HashMap<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("date", rs.getDate("date").toString()); // 日付を文字列として格納
                dataPoint.put("mood", rs.getInt("mood")); // 気分スコア
                moodData.add(dataPoint);
            }

            // Gson を使用して JSON に変換
            Gson gson = new Gson();
            String moodDataJson = gson.toJson(moodData);

            // JSP にデータを渡す
            request.setAttribute("moodDataJson", moodDataJson);
            request.setAttribute("userId", userId);

            // チャート用の JSP にフォワード
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/chart.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // データベースエラーの場合はエラーページを表示
            request.setAttribute("error", "database_error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/admin.jsp");
            dispatcher.forward(request, response);
        }
    }
}
