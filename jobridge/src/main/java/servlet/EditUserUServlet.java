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

@WebServlet("/EditUserUServlet")
public class EditUserUServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("user_id");

        if (userId == null || userId.isEmpty()) {
            session.setAttribute("error", "利用者IDが取得できませんでした。");
            response.sendRedirect("./user_main.jsp");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT user_id, username, password FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("user_id", rs.getString("user_id"));
                request.setAttribute("username", rs.getString("username"));
                request.setAttribute("password", rs.getString("password"));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_user_form_u.jsp");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute("error", "利用者情報が見つかりません。");
                response.sendRedirect("./user_main.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "データベースエラーが発生しました。");
            response.sendRedirect("./user_main.jsp");
        }
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
                RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/edit_user_form_u.jsp");
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
                    response.sendRedirect("index.jsp?success=user_updated");
                } else {
                    request.setAttribute("error", "更新に失敗しました。");
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