<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>メインページ</title>
</head>
<body>
    <h1>その日の気分を教えてください</h1>
    <form action="UserServlet" method="post">
        <select name="mood">
            <option value="5">絶好調</option>
            <option value="4">好調</option>
            <option value="3">普通</option>
            <option value="2">不調</option>
            <option value="1">絶不調</option>
        </select>
        <textarea name="comment" maxlength="128" placeholder="コメント (任意)"></textarea>
        <button type="submit">送信</button>
    </form>
</body>
</html>
