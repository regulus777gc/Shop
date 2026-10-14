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
ユーザーID:<input type="text" name="userId"><br>
パスワード:<input type="password" name="pass" ><br>
性別:<input type="radio" name="seibetu" value="he" >男性
<input type="radio" name="seibetu" value="she" >女性
<input type="radio" name="seibetu" value="nazo" >その他、答えない
年齢:<input type="text" name="age" ><br>
<input type="submit" value="新規登録">
<h2>この内容で登録します</h2>
        <table id="result" class="result">
</body>
</html>