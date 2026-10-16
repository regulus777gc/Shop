<!-- カートの中身 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カートの中身</title>
</head>
<body>
<% List<String> 変数１ = (List<String>)request.getAttribute("ここに変数１"); %>
<% for(String 変数２: 変数１) { %>
        <%= 変数２ %><br>
    <% } %>
</body>
</html>