<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
	<link rel="icon" type="image/x-icon" href="./favicon.ico">
    <title>管理者メイン</title>
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
                <th>ニックネーム</th>
                <th>日付</th>
                <th>気持ち</th>
                <th>コメント</th>
                <th>グラフ</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userData}">
                <tr>
                    <!-- 利用者IDをリンク化 -->
                    <td>${user.user_id}</td>
                    <td>${user.username}</td>
                    <td>${user.date}</td>
                    <td>
					    <c:choose>
					        <c:when test="${user.mood == '5'}">絶好調</c:when>
					        <c:when test="${user.mood == '4'}">好調</c:when>
					        <c:when test="${user.mood == '3'}">普通</c:when>
					        <c:when test="${user.mood == '2'}">不調</c:when>
					        <c:when test="${user.mood == '1'}">絶不調</c:when>
					        <c:otherwise>未登録</c:otherwise>
					    </c:choose>
                    </td>
                    <td>${user.comment}<br><a href="UserHistoryServlet?user_id=${user.user_id}"><small>コメント一覧</small></a></td>
                    <!-- グラフへのリンク -->
                    <td>
                        <a href="ChartServlet?user_id=${user.user_id}">選択</a>
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
    <div class="container">
    <a href="./EditUserServlet">利用者情報の変更</a> | 
    <a href="./EditAdminServlet">管理者情報の変更</a> |
    <a href="./LogoutServlet">ログアウト</a>
    </div>
</body>
</html>
