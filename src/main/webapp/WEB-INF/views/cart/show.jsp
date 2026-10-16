<%-- カートの中身 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>MORI MARKET - ショッピングカート</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

	<main class="main">
		<h2>MORI MARKET / ショッピングカート</h2>

		<c:if test="${not empty sessionScope.flash}">
			<p class="flash"><c:out value="${sessionScope.flash}"/></p>
			<c:remove var="flash" scope="session"/>
		</c:if>

		<c:choose>
			<c:when test="${cart.empty}">
				<p>カートは空です。</p>
				<a href="${pageContext.request.contextPath}/products" class="btn-secondary">買い物を続ける</a>
			</c:when>

			<c:otherwise>
				<div class="cart-ui">
					<div class="cart-table-area">
						<table class="cart-table">
							<thead>
								<tr>
									<th>商品</th>
									<th>価格</th>
									<th>数量</th>
									<th>小計</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${cart.items}">
									<tr>
										<td><c:out value="${item.product.name}"/></td>
										<td><my:yen value="${item.product.price}"/></td>
										<td>
											<form action="${pageContext.request.contextPath}/cart" method="post" class="update-form">
												<input type="hidden" name="_csrf" value="${sessionScope.csrf}">
												<input type="hidden" name="action" value="update">
												<input type="hidden" name="productId" value="${item.product.id}">
												<input type="number" name="quantity" value="${item.quantity}" min="1" max="99" class="quantity-input">
												<button type="submit" class="btn-update">変更</button>
											</form>
										</td>
										<td><my:yen value="${item.subtotal}"/></td>
										<td>
											<form action="${pageContext.request.contextPath}/cart" method="post">
												<input type="hidden" name="_csrf" value="${sessionScope.csrf}">
												<input type="hidden" name="action" value="remove">
												<input type="hidden" name="productId" value="${item.product.id}">
												<button type="submit" class="btn-delete">削除</button>
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<div class="cart-actions-left">
							<a href="${pageContext.request.contextPath}/products" class="btn-secondary">買い物を続ける</a>
						</div>
					</div>

					<div class="cart-summary-area">
						<div class="order-summary-box">
							<h3>ご注文内容</h3>

							<jsp:include page="/WEB-INF/views/common/totals.jsp"/>

							<div class="checkout-action">
								<a href="${pageContext.request.contextPath}/checkout" class="btn-primary">お支払いへ進む</a>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>