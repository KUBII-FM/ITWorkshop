<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>管理者ページ</title>
</head>
<body>
    <h1>本日の登録情報</h1>

    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- テーブル表示 -->
    <table>
        <thead>
            <tr>
                <th>利用者ID</th>
                <th>ユーザー名</th>
                <th>日付</th>
                <th>気持ち</th>
                <th>コメント</th>
                <th>アクション</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userData}">
                <tr>
                    <!-- 利用者IDをリンク化 -->
                    <td>
                        <a href="ChartServlet?user_id=${user.user_id}&period=month">${user.user_id}</a>
                    </td>
                    <td>${user.username}</td>
                    <td>${user.date}</td>
                    <td>${user.mood}</td>
                    <td>${user.comment}</td>
                    <!-- グラフへのリンク -->
                    <td>
                        <a href="ChartServlet?user_id=${user.user_id}">グラフを見る</a>
                    </td>
                </tr>
            </c:forEach>
            <!-- データがない場合のメッセージ -->
            <c:if test="${empty userData}">
                <tr>
                    <td colspan="6">本日の登録データはありません。</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    <a href="./LogoutServlet">ログアウト</a>
</body>
</html>
