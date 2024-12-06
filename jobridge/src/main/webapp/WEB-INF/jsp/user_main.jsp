<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>利用者メインページ</title>
</head>
<body>
    <div class="container">
        <h1>利用者メインページ</h1>
        <p>ようこそ、<strong>${sessionScope.username}</strong>さん</p>

        <!-- 成功・エラーメッセージ -->
        <c:if test="${param.error == 'database_error'}">
            <p class="error">データベースエラーが発生しました。もう一度お試しください。</p>
        </c:if>
        <c:if test="${param.success == 'entry_added'}">
            <p class="success">今日の気持ちが登録されました。</p>
        </c:if>

        <!-- 登録済みデータの表示 -->
        <h2>今日の登録内容</h2>
        <c:choose>
            <c:when test="${not empty requestScope.mood}">
                <table>
                    <thead>
                        <tr>
                            <th>登録日</th>
                            <th>気持ち</th>
                            <th>コメント</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${requestScope.date}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${requestScope.mood == 1}">絶不調</c:when>
                                    <c:when test="${requestScope.mood == 2}">不調</c:when>
                                    <c:when test="${requestScope.mood == 3}">普通</c:when>
                                    <c:when test="${requestScope.mood == 4}">好調</c:when>
                                    <c:when test="${requestScope.mood == 5}">絶好調</c:when>
                                </c:choose>
                            </td>
                            <td>${requestScope.comment}</td>
                        </tr>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>今日の登録データはまだありません。</p>
            </c:otherwise>
        </c:choose>

        <!-- 今日の気持ちを登録するフォーム -->
        <h2>今日の気持ちを登録</h2>
        <form action="MoodServlet" method="post">
            <label for="mood">今日の気持ちを選択してください (1～5):</label>
            <select id="mood" name="mood" required>
                <option value="">選択してください</option>
                <option value="1">絶不調</option>
                <option value="2">不調</option>
                <option value="3">普通</option>
                <option value="4">好調</option>
                <option value="5">絶好調</option>
            </select>

            <label for="comment">コメント 最大100文字 (任意):</label>
            <textarea id="comment" name="comment" rows="4" placeholder="コメントを入力してください"></textarea>

            <button type="submit">登録</button>
        </form>

        <!-- ログアウトリンク -->
        <a href="./LogoutServlet">ログアウト</a>
    </div>
</body>
</html>
