<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&family=M+PLUS+Rounded+1c&display=swap" rel="stylesheet">
    <title>利用者一覧と修正</title>
</head>
<body>
    <h1>利用者一覧</h1>
        
    <c:if test="${param.success == 'user_updated'}">
        <p class="success">更新が完了しました</p>
    </c:if>
    <c:if test="${param.success == 'user_deleted'}">
        <p class="success">削除が完了しました</p>
    </c:if>       
    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- 利用者一覧の表示 -->
    <table>
        <thead>
            <tr>
                <th>利用者ID</th>
                <th>ユーザー名</th>
                <th>修正/削除</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td>${user.user_id}</td>
                    <td>${user.username}</td>
                    <td>
                        <a href="EditUserFormServlet?user_id=${user.user_id}">選択</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty userList}">
                <tr>
                    <td colspan="3">登録済みの利用者はありません。</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    <div class="container">
    <a href="AdminServlet">本日の登録情報に戻る</a> | 
    <a href="./LogoutServlet">ログアウト</a>
    </div>
</body>
</html>
