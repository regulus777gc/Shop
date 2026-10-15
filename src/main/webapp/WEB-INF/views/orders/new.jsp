<!-- お届け先の入力フォーム -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>お届け先の入力</title>
</head>
<body>
<form action="LoginServlet" method="post">
郵便番号:<input type="text" name="zip" ><br>
住所:<input type="text" name="address" ><br>
<input type="submit" value="登録">
<h2>この住所で登録します</h2>
        <table id="result" class="result">
</body>
</html>