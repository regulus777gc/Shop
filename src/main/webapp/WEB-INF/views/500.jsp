<!-- エラー画面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 システムエラー</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="error-container">
    <div class="error-code-500">500</div>
    <h1>システムエラーが発生しました</h1>
    <p>
        ただいま一時的な障害が発生しているか、アクセスが集中しております。<br>
        大変お手数ですが、しばらく時間をおいてから再度お試しください<br>
    </p>
    
    <!-- 開発社用 -->
    <% if (exception != null) {%>
        <div class="error-details">
            <strong>エラー詳細</strong>
        </div>
    <a href="header.jsp">トップへ戻る</a>
    </div>
</body>
</html>