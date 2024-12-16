<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
    <title>管理者情報の変更</title>
</head>
<body>
    <h1>管理者情報の変更</h1>

    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- 成功メッセージの表示 -->
    <c:if test="${not empty success}">
        <p class="success">${success}</p>
    </c:if>

    <!-- 管理者情報変更フォーム -->
    <form action="EditAdminServlet" method="post">
        <label for="admin_id">新しい管理者ID:</label>
        <input type="text" id="admin_id" name="admin_id" value="${admin_id}" required>

        <label for="password">新しいパスワード:</label>
        <input type="password" id="password" name="password" required>
        
        <label for="confirm_password">パスワード確認:</label>
        <input type="password" id="confirm_password" name="confirm_password" required>

        <button type="submit">変更を保存</button>
    </form>
    <div class="container">
    <a href="AdminServlet">本日の登録情報に戻る</a> | 
    <a href="./LogoutServlet">ログアウト</a>
    </div>
</body>
</html>