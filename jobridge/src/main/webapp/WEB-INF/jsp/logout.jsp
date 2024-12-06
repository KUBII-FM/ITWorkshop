<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>ログアウト</title>
</head>
<body>
    <h1>ログアウト</h1>

    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- トップページへのリンク -->
    <p>ログアウトしました</p>
    <a href="index.jsp">トップページに戻る</a>
</body>
</html>
