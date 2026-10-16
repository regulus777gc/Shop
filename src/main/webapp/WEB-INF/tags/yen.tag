<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ attribute name="value" required="true" type="java.lang.Integer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

¥<fmt:formatNumber value="${value}" pattern="#,###" />