<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
    <title>利用者一覧と修正</title>
</head>
<body>
    <h1>利用者一覧</h1>
	<div class="container">
    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- 利用者一覧の表示 -->
    <table>
        <thead>
            <tr>
                <th>利用者ID</th>
                <th>ユーザー名</th>
                <th>修正/削除</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td>${user.user_id}</td>
                    <td>${user.username}</td>
                    <td>
                        <a href="EditUserServlet?user_id=${user.user_id}">選択</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty userList}">
                <tr>
                    <td colspan="3">登録済みの利用者はありません。</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    </div>

    <!-- 個別利用者情報の修正フォーム -->
    <c:if test="${not empty user_id}">
        <h3>利用者情報の修正/削除</h3>
        <form action="EditUserServlet" method="post">
            <label for="user_id">利用者ID:</label>
            <input type="text" id="user_id" name="user_id" value="${user_id}" readonly>

            <label for="username">ユーザー名:</label>
            <input type="text" id="username" name="username" value="${username}" required>

            <label for="password">パスワード:</label>
            <input type="password" id="password" name="password" value="${password}" required>

            <button type="submit">修正</button>
            <button href="DeleteUserServlet?user_id=${user.user_id}" onclick="return confirm('本当に削除しますか？');">削除</button>
        </form>
    </c:if>
    <div class="container">
    <a href="AdminServlet">管理者ページに戻る</a>
    </div>
</body>
</html>
