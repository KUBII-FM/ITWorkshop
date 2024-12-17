package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DatabaseUtil;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String adminId = (String) session.getAttribute("admin_id");

        if (adminId == null) {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/admin_login.jsp?error=not_logged_in");
        	dispatcher.forward(request, response);
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
        	String sql = "SELECT u.user_id, u.username, e.date, e.mood, e.comment " +
                    "FROM users u " +
                    "LEFT JOIN entries e ON u.user_id = e.user_id AND e.date = CURDATE()" +
                    "ORDER BY u.user_id ASC"; // 昇順に並べ替え;
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    ResultSet rs = stmt.executeQuery();

            ArrayList<HashMap<String, String>> userData = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> userRecord = new HashMap<>();
                userRecord.put("user_id", rs.getString("user_id"));
                userRecord.put("username", rs.getString("username"));
                userRecord.put("date", rs.getString("date") != null ? rs.getString("date") : "未登録");
                userRecord.put("mood", rs.getString("mood") != null ? rs.getString("mood") : "未登録");
                userRecord.put("comment", rs.getString("comment") != null ? rs.getString("comment") : "未登録");
                userData.add(userRecord);
            }

            request.setAttribute("userData", userData);
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/admin.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "データベースエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/admin.jsp");
            dispatcher.forward(request, response);
        }
    }
}