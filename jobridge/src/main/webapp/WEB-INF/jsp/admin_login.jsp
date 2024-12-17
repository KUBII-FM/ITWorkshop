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
    <title>管理者ログイン</title>
</head>
<body>
    <h1>ココログ</h1>
    
    <!-- 成功メッセージの表示 -->
    <c:if test="${param.success == 'updated'}">
        <p class="success">管理者情報が更新されました。再度ログインしてください。</p>
    </c:if>
    <!-- エラーメッセージの表示 -->
    <c:if test="${param.error == 'invalid_credentials'}">
        <p class="error">ログインに失敗しました。IDまたはパスワードをご確認ください。</p>
    </c:if>    
    
    <form action="./AdminLoginServlet" method="post">
    	<h3>管理者ログイン</h3>
        <label for="admin_id">管理者ID:</label>
        <input type="text" id="admin_id" name="admin_id" required>
        <label for="password">パスワード:</label>
        <input type="password" id="password" name="password" required>
        <button type="submit">ログイン</button>
    </form>
    <div class="container">
    <a href="./index.jsp">トップページに戻る</a>
    </div>
</body>
</html>
