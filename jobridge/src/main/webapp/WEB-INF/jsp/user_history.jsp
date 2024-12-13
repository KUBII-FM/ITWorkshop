<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
    <title>過去30日間のコメント一覧</title>
</head>
<body>
    <h1>利用者 ${userId} のコメント一覧</h1>

    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- コメント一覧テーブル -->
    <table>
        <thead>
            <tr>
                <th>日付</th>
                <th>気持ち</th>
                <th>コメント</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="comment" items="${commentData}">
                <tr>
                    <td>${comment.date}</td>
                    <td>
                        <c:choose>
                            <c:when test="${comment.mood == '1'}">絶不調</c:when>
                            <c:when test="${comment.mood == '2'}">不調</c:when>
                            <c:when test="${comment.mood == '3'}">普通</c:when>
                            <c:when test="${comment.mood == '4'}">好調</c:when>
                            <c:when test="${comment.mood == '5'}">絶好調</c:when>
                            <c:otherwise>不明</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${comment.comment}</td>
                </tr>
            </c:forEach>
            <!-- データがない場合のメッセージ -->
            <c:if test="${empty commentData}">
                <tr>
                    <td colspan="3">過去30日間のコメントはありません。</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    
    <!-- 期間選択フォーム -->
	<form action="UserHistoryServlet" method="get" class="date-range-form">
	    <input type="hidden" name="user_id" value="${userId}">
	    <label for="start_date">開始日:</label>
	    <input type="date" id="start_date" name="start_date" required>
	    <label for="end_date">終了日:</label>
	    <input type="date" id="end_date" name="end_date" required>
	    <button type="submit">適用</button>
	</form>

    <div class="container">
        <a href="AdminServlet">管理者ページに戻る</a>
    </div>
</body>
</html>
