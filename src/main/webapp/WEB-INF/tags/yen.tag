<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ attribute name="value" required="true" type="java.lang.Integer" %>
¥<fmt:formatNumber value="${value}" pattern="#,##0"/>