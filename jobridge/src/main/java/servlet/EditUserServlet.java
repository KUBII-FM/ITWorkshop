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

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/edit_user.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("user_id");

        if ("update".equals(action)) {
            // 修正処理
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");

            if (userId == null || username == null || password == null || username.isEmpty() || password.isEmpty()) {
                request.setAttribute("error", "すべてのフィールドを入力してください。");
                doGet(request, response);
                return;
            }
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "パスワードが一致していません。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/edit_user_form.jsp");
                dispatcher.forward(request, response);
                return;
            }

            try (Connection conn = DatabaseUtil.getConnection()) {
                String sql = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, userId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("EditUserServlet?success=user_updated");
                } else {
                    request.setAttribute("error", "更新に失敗しました。");
                    doGet(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "データベースエラーが発生しました。");
                doGet(request, response);
            }
        } else if ("delete".equals(action)) {
            // 削除処理
            try (Connection conn = DatabaseUtil.getConnection()) {
                String sql = "DELETE FROM users WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, userId);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    response.sendRedirect("EditUserServlet?success=user_deleted");
                } else {
                    request.setAttribute("error", "削除に失敗しました。");
                    doGet(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "データベースエラーが発生しました。");
                doGet(request, response);
            }
        } else {
            request.setAttribute("error", "無効なアクションです。");
            doGet(request, response);
        }
    }
}