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
    <title>利用者詳細情報</title>
</head>
<body>
<div class="container">
    <h1>利用者詳細情報</h1>
    <table border="1">
        <tr>
            <th>日付</th>
            <th>気持ち</th>
            <th>コメント</th>
        </tr>
        <c:forEach var="entry" items="${userDetails}">
            <tr>
                <td>${entry.date}</td>
                <td>${entry.mood}</td>
                <td>${entry.comment}</td>
            </tr>
        </c:forEach>
    </table>
    
    <!-- 期間選択フォーム -->
    <form action="ChartServlet" method="get">
        <input type="hidden" name="user_id" value="${userId}">
        <label for="start_date">開始日:</label>
        <input type="date" id="start_date" name="start_date" required>
        <label for="end_date">終了日:</label>
        <input type="date" id="end_date" name="end_date" required>
        <button type="submit">期間を適用</button>
    </form>
    <a href="AdminServlet">管理者ページに戻る</a>
    </div>
</body>
</html>
