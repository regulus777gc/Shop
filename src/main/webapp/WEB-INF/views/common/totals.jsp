<!-- 小計・送料・合計の表示 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagbir="/WEB-INF/tags" %>
<!DOCTYPE html>

<div class="totals">
	<table class="totals-table">
		<tr>
			<th>商品点数</th>
			<td><c:out value="${cart.totalQuantity}" />点</td>
		</tr>
		
		<tr>
			<th>小計</th>
			<td><my:yen value="${cart.subtotal}"/></td>
		</tr>
		
		<tr>
			<th>送料</th>
			<td><my:yen value="${cart.shoppingFee}"/></td>
		</tr>
		
		<tr class="total-tal">
			<th>合計</th>
			<td><strong><my:yen value="${cart.totalPrice}"/></strong></td>
		</tr>
	</table>
</div>