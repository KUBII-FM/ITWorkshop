<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>利用者詳細情報</title>
</head>
<body>
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
    <form action="ChartServlet" method="get">
        <input type="hidden" name="user_id" value="${userId}">
        <label>期間を選択:</label>
        <select name="period">
            <option value="month">当月</option>
            <option value="30days">過去30日</option>
            <option value="custom">指定期間</option>
        </select>
        <br>
        <label>開始日:</label>
        <input type="date" name="start_date">
        <label>終了日:</label>
        <input type="date" name="end_date">
        <br>
        <button type="submit">グラフを表示</button>
    </form>
    <a href="AdminServlet">管理者ページに戻る</a>
</body>
</html>
