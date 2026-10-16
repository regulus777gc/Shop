<%-- 小計・送料・合計の表示（cart/show.jsp と orders/new.jsp から include される部品） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<div class="totals">
	<table class="totals-table">
		<tr>
			<th>商品点数</th>
			<td><c:out value="${cart.count}" />点</td>
		</tr>

		<tr>
			<th>小計</th>
			<td><my:yen value="${cart.subtotal}"/></td>
		</tr>

		<tr>
			<th>送料</th>
			<td><my:yen value="${cart.shipping}"/></td>
		</tr>

		<tr class="total-row">
			<th>合計</th>
			<td><strong><my:yen value="${cart.total}"/></strong></td>
		</tr>
	</table>

	<c:if test="${cart.remainingForFree > 0}">
		<p class="free-shipping-note">あと <my:yen value="${cart.remainingForFree}"/> で送料無料です。</p>
	</c:if>
</div>