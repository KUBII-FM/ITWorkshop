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

@WebServlet("/EditAdminServlet")
public class EditAdminServlet extends HttpServlet {
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String adminId = (String) session.getAttribute("admin_id");

        if (adminId == null) {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("admin_login.jsp?error=not_logged_in");
        	dispatcher.forward(request, response);
        }

        // 必要であれば、現在の管理者情報を取得してJSPに渡す
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT admin_id FROM admins WHERE admin_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, adminId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("admin_id", adminId);
            } else {
                request.setAttribute("error", "管理者情報が見つかりません。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "データベースエラーが発生しました。");
        }

        // JSP にフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_admin.jsp");
        dispatcher.forward(request, response);
    }
	
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String adminId = (String) session.getAttribute("admin_id");
        String currentAdminId = (String) session.getAttribute("admin_id");

        if (adminId == null) {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin_login.jsp?error=not_logged_in");
            dispatcher.forward(request, response);
        }
        
        String newAdminId = request.getParameter("admin_id");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (newAdminId == null || newAdminId.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                request.setAttribute("error", "すべてのフィールドを入力してください。");
                request.getRequestDispatcher("/WEB-INF/jsp/edit_admin.jsp").forward(request, response);
                return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "パスワードが一致していません。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_admin.jsp");
            dispatcher.forward(request, response);
            return;
        }
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE admins SET admin_id = ?, password = ? WHERE admin_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newAdminId);
            stmt.setString(2, password);
            stmt.setString(3, currentAdminId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                // セッションを無効化してログアウト
                session.invalidate();
                // ログインページにリダイレクトし、メッセージを表示
                response.sendRedirect("AdminLoginServlet?success=updated");
                return; // リダイレクト後、これ以上処理を続けない
            } else {
                request.setAttribute("error", "情報の更新に失敗しました。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "データベースエラーが発生しました。");
        }

        request.getRequestDispatcher("/WEB-INF/jsp/edit_admin.jsp").forward(request, response);
    }
}
