<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
	<link rel="icon" type="image/x-icon" href="favicon.ico">
    <title>ココログ - トップページ</title>
</head>
<body>
    <h1>ココログ</h1>   
    
    <!-- 成功メッセージの表示 -->
    <c:if test="${param.success == 'registered'}">
        <p class="success">登録が完了しました。</p>
    </c:if>
    <c:if test="${param.success == 'logout'}">
        <p class="success">ログアウトしました。</p>
    </c:if>
    <c:if test="${param.success == 'user_updated'}">
        <p class="success">登録情報が更新されました。</p>
    </c:if>
    
    <!-- エラーメッセージの表示 -->
    <c:if test="${param.error == '1'}">
        <p class="error">ログインに失敗しました。IDまたはパスワードをご確認ください。</p>
    </c:if>
    <c:if test="${param.error == '2'}">
        <p class="error">システムエラーが発生しました。再試行してください。</p>
    </c:if>
    <c:if test="${param.error == '3'}">
        <p class="error">セッションが切れました。再度ログインしてください。</p>
    </c:if>
    
    <form action="./LoginServlet" method="post">
        <h3>利用者ログイン</h3>
        <label for="user_id">利用者ID:</label>
        <br>
        <input type="text" id="user_id" name="user_id" required>
        <br>
        <label for="password">パスワード:</label>
        <br>
        <input type="password" id="password" name="password" required>
        <br>
        <button type="submit">ログイン</button>
    </form>
    <div class="container">
    <a href="RegisterServlet">新規登録はこちら</a> |
    <a href="AdminLoginServlet">管理者ログインはこちら</a>
    </div>
</body>
</html>
