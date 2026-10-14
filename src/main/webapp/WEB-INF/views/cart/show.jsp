<!-- カートの中身 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カートの中身</title>
</head>
<body>
<% List<String> books = (List<String>)request.getAttribute("books"); %>
<% for(String title: books) { %>
        <%= title %><br>
    <% } %>
</body>
</html>