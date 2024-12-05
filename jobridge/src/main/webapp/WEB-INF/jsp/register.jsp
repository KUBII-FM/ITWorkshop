<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>新規登録</title>
</head>
<body>
    <h1>新規登録</h1>
    <form action="../RegisterServlet" method="post">
        <label for="user_id">利用者番号:</label>
        <input type="text" id="user_id" name="user_id" required>
        <label for="username">ニックネーム:</label>
        <input type="text" id="username" name="username" required>
        <label for="password">パスワード:</label>
        <input type="password" id="password" name="password" required>
        <label for="confirm_password">パスワード確認:</label>
        <input type="password" id="confirm_password" name="confirm_password" required>
        <button type="submit">登録</button>
    </form>
    <a href="../index.jsp">トップページに戻る</a>
</body>
</html>
