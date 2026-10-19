<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MORI MARKET</title>
</head>
<body>
<form action="LoginServlet" method="post">
名前:<input type="text" name="name"><br>
メールアドレス:<input type="text" name="email" ><br>
<input type="submit" value="ログイン">
<p>初めての方はこちら<a href="newAccount.jsp">新規登録</a></p>
</form>
</body>
</html>