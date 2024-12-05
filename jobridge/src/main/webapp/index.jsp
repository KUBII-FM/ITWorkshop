<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>メンタル管理ツール - トップページ</title>
</head>
<body>
    <h1>メンタル管理ツール</h1>
    <form action="LoginServlet" method="post">
        <h2>利用者ログイン</h2>
        <label for="user_id">利用者番号:</label>
        <input type="text" id="user_id" name="user_id" required>
        <br>
        <label for="password">パスワード:</label>
        <input type="password" id="password" name="password" required>
        <br>
        <button type="submit">ログイン</button>
    </form>

    <h3>新規登録</h3>
    <a href="WEB-INF/jsp/register.jsp">新規登録はこちら</a>
    <br>
    
    <h3>管理者ログイン</h3>
    <a href="./AdminLoginServlet">管理者ログインはこちら</a>
</body>
</html>
