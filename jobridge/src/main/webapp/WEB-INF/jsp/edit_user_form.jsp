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
	<link rel="icon" type="image/x-icon" href="../favicon.ico">
    <title>利用者情報の変更/削除</title>
</head>
<body>
    <h1>利用者情報の変更/削除</h1>

    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- 修正フォーム -->
	<form action="EditUserServlet" method="post">
	    <label for="user_id">利用者ID:</label>
	    <br>
	    <input type="text" id="user_id" name="user_id" value="${user_id}" readonly>
		<br>
	    <label for="username">ニックネーム:</label>
	    <br>
	    <input type="text" id="username" name="username" value="${username}" required>
		<br>
	    <label for="password">パスワード:</label>
	    <br>
	    <input type="password" id="password" name="password" value="${password}" required>
	    <br>
        <label for="confirm_password">パスワード確認:</label>
        <br>
        <input type="password" id="confirm_password" name="confirm_password" value="${password}" required>
		<br>
	    <!-- 修正ボタン -->
	    <button type="submit" name="action" value="update">変更</button>
	
	    <!-- 削除ボタン -->
	    <button type="submit" name="action" value="delete" onclick="return confirm('本当に削除しますか？');">削除</button>
	</form>
    <div class="container">
    <a href="EditUserServlet">利用者一覧に戻る</a> | 
    <a href="./LogoutServlet">ログアウト</a>
    </div>
</body>
</html>
