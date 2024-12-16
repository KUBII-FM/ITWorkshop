<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
    <title>利用者メインページ</title>
</head>
<body>
        <h1>ココログ</h1>
        
        <div class="container">
        <p>ようこそ、<strong>${sessionScope.username}</strong>さん</p>

        <!-- 成功・エラーメッセージ -->
        <c:if test="${param.error == 'database_error'}">
            <p class="error">データベースエラーが発生しました。もう一度お試しください。</p>
        </c:if>
        <c:if test="${param.success == 'entry_added'}">
            <p class="success">今日の気持ちが登録されました。</p>
        </c:if>

	    <!-- 今日の登録済みデータ表示 -->
	    <h3>今日の登録内容</h3>
	    <c:if test="${not empty mood}">
	        <p><strong>気持ち:</strong> 
	            <c:choose>
	                <c:when test="${mood == 1}">絶不調</c:when>
	                <c:when test="${mood == 2}">不調</c:when>
	                <c:when test="${mood == 3}">普通</c:when>
	                <c:when test="${mood == 4}">好調</c:when>
	                <c:when test="${mood == 5}">絶好調</c:when>
	            </c:choose>
	        </p>
	        <p><strong>コメント:</strong> ${comment}</p>
	    </c:if>
	    </div>
	
	    <!-- 今日の気持ちとコメントを入力するフォーム -->
	    <h2>今日の気持ちを登録 / 編集</h2>
	    <form action="MoodServlet" method="post">
	        <label for="mood">今日の気持ちを選択してください :</label>
	        <select id="mood" name="mood" required>
	            <option value="">選択してください</option>
	            <option value="1" ${mood == 1 ? 'selected' : ''}>絶不調</option>
	            <option value="2" ${mood == 2 ? 'selected' : ''}>不調</option>
	            <option value="3" ${mood == 3 ? 'selected' : ''}>普通</option>
	            <option value="4" ${mood == 4 ? 'selected' : ''}>好調</option>
	            <option value="5" ${mood == 5 ? 'selected' : ''}>絶好調</option>
	        </select>
            <label for="comment">コメント 最大128文字 (任意):</label>
            <textarea id="comment" name="comment" rows="4" placeholder="コメントを入力してください">${comment}</textarea>
        <button type="submit">登録 / 更新</button>
    </form>
	<div class="container">
    <!-- ログアウトリンク -->
    <a href="./LogoutServlet">ログアウト</a>
    </div>
</body>
</html>
