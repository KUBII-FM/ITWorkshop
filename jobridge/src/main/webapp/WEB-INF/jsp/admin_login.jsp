<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
    <title>管理者ログイン</title>
</head>
<body>
    <h1>管理者ログイン</h1>
    <form action="./AdminLoginServlet" method="post">
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
