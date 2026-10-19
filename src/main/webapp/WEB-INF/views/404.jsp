<!-- エラー画面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404　ページが見つかりません</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>
<body>
    <div class="error-container">
    <h1>お探しのページが見つかりません</h1>
    <p>
        お探しのページは、移動または削除されたか、<br>
        URLに誤りがある可能性があります。<br>
        お手数ですが、トップページより再度お探しください。<br>
    </p>
    <a href="${pageContext.request.contextPath}/products">トップへ戻る</a>
    </div>
</body>
</html>