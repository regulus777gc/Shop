<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
	
	<header class="header">
		<div class="header-top">
			<h1 class="logo">
				<a href="${pageContext.request.contextPath}/products">MORI MARKET</a>
			</h1>

	<div class="search">
		<form action="${pageContext.request.contextPath}/products" method="get">
		<input type="text" name="word" placeholder="商品を検索" value="<c:out value="${param.word}"/>">
		<button type="submit">検索</button>
	</form>
</div>
	
	<div class="headerA">
		<a href="${pageContext.request.contextPath}/cart" class="cart-link">
		カート <c:out value="${not empty sessionScope.cart ? sessionScope.cart.totalQuantity : 0}"/>点
		</a>
	</div>
</div>

	<nav class="category-nav">
		<ul>
			<li><a href="${pageContext.request.contextPath}/products">全て</a></li>
	            	<li><a href="${pageContext.request.contextPath}/products?category=new">新着</a></li>
	            	<li><a href="${pageContext.request.contextPath}/products?category=lifestyle">暮らし</a></li>
	            	<li><a href="${pageContext.request.contextPath}/products?category=fashion">服</a></li>
	            	<li><a href="${pageContext.request.contextPath}/products?category=food">食品</a></li>
	            	<li><a href="${pageContext.request.contextPath}/products?category=gift">ギフト</a></li>
		</ul>
	</nav>
</header>
