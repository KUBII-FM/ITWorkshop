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
	<link rel="icon" type="image/x-icon" href="./favicon.ico">
    <title>新規登録</title>
</head>
<body>
    <h1>新規登録</h1>
    <div class="container">
    <!-- エラーメッセージの表示 -->
	<c:if test="${not empty error}">
	    <p class="error">
	        <c:choose>
	            <c:when test="${error == 'user_or_username_exists'}">
	                利用者番号またはニックネームがすでに使用されています。
	            </c:when>
	            <c:when test="${error == 'password_mismatch'}">
	                パスワードが一致しません。
	            </c:when>
	            <c:when test="${error == 'database_error'}">
	                データベースエラーが発生しました。もう一度お試しください。
	            </c:when>
	            <c:otherwise>
	                不明なエラーが発生しました。
	            </c:otherwise>
	        </c:choose>
	    </p>
	</c:if>
	<!-- 登録完了メッセージの表示 -->
	<c:if test="${not empty success}">
	    <p class="success">登録が完了しました。<br><a href="./index.jsp">トップページ</a>へ戻ってログインしてください。</p>
	</c:if>
	</div>
    
    <form action="./RegisterServlet" method="post">
        <label for="user_id">利用者番号:</label>
        <br>
        <input type="text" id="user_id" name="user_id" value="${param.user_id}" required>
		<br>
        <label for="username">ニックネーム:</label>
        <br>
        <input type="text" id="username" name="username" value="${param.username}" required>
		<br>
        <label for="password">パスワード:</label>
        <br>
        <input type="password" id="password" name="password" value="${param.password}" required>
        <br>
        <label for="confirm_password">パスワード確認:</label>
        <br>
        <input type="password" id="confirm_password" name="confirm_password" required>
		<br>
        <button type="submit">登録</button>
    </form>
    <div class="container">
    <a href="./index.jsp">トップページに戻る</a>
    </div>
</body>
</html>
