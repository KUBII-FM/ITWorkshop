<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
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
    <a href="./index.jsp">トップページに戻る</a>
</body>
</html>
