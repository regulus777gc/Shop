<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MORI MARKET</title>
</head>
<body>
<form action="Customer" method="post">
名前:<input type="text" name="name"><br>
メールアドレス:<input type="text" name="email" ><br>
<input type="submit" value="新規登録">
<h2>この内容で登録します</h2>
        <table id="result" class="result">
</body>
</html>