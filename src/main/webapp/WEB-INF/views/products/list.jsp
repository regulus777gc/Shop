<!-- 商品一覧 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
<body id="top">
<main>
<div class="items">
                <div class="item">
                    <img src="ここに画像" alt="ここに商品の名前">
                    <p>
                        <c:out value="${Product,name}"/><br>
                        <strong>ここに商品の金額</strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
                <div class="item">
                    <img src="" alt="">
                    <p>
                         <c:out value="${Product,name}"/><br>
                        <strong><c:out value="${Product,price}"/></strong>
                    </p>
                </div>
            </div>
</main>
<footer>
            <div class="gotop">
                <a href="#top"><img src="" alt="ページトップへ戻る"></a>
            </div>
            <p class="">&copy; </p>
        
        </footer>
</body>
</html>