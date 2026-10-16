<!-- カートの中身 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import=" CartServlet.java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カートの中身</title>
</head>
<body>
<% List<String> cart = (List<String>)request.getAttribute("cart"); %>
<% for(String 変数２: cart) { %>
        <%= 変数２ %><br>
    <% } %>
</body>
</html>