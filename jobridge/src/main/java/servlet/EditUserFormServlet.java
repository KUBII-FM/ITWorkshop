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
import model.DatabaseUtil;

@WebServlet("/EditUserFormServlet")
public class EditUserFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");

        try (Connection conn = DatabaseUtil.getConnection()) {
            // ユーザー一覧を取得
            String sql = "SELECT user_id, username FROM users ORDER BY user_id ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ArrayList<HashMap<String, String>> userList = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> user = new HashMap<>();
                user.put("user_id", rs.getString("user_id"));
                user.put("username", rs.getString("username"));
                userList.add(user);
            }

            // 利用者IDが指定されている場合、その利用者情報を取得
            if (userId != null && !userId.isEmpty()) {
                sql = "SELECT user_id, username, password FROM users WHERE user_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, userId);
                ResultSet userRs = stmt.executeQuery();
                if (userRs.next()) {
                    request.setAttribute("user_id", userRs.getString("user_id"));
                    request.setAttribute("username", userRs.getString("username"));
                    request.setAttribute("password", userRs.getString("password"));
                } else {
                    request.setAttribute("error", "指定された利用者が見つかりません。");
                }
            }

            request.setAttribute("userList", userList);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "データベースエラーが発生しました。");
        }

        // JSP にデータを渡す
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_user_form.jsp");
        dispatcher.forward(request, response);
    }

}
