<!--　商品のアイコン　-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="label" required="true" type="java.lang.String" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ attribute name="value" required="false" type="java.lang.String" %>
<%@ attribute name="error" required="false" type="java.lang.String" %>


<div class="form-field">
<label for="${name}"><c:out value="${label}"/></label>
<input type="${empty type ? 'text' : type}"
id="${name}" name="${name}"
value="<c:out value="${value}"/>"
class="${not empty error ? 'error-input' : ''}" />
<c:if test="${not empty error}">
<span class="error-message"><c:out value="${error}"/></span>
</c:if>
</div>