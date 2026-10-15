<!-- 商品の詳細 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品詳細</title>
</head>
<body>
<img src="" alt="">
        <p>
           <c:out value="${Product,name}"/><br>
           <strong><c:out value="${Product,price}"/></strong>
        </p>
</body>
</html>