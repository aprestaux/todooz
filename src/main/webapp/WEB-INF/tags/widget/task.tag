<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="task" required="true" type="fr.todooz.domain.Task" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div>
    <p><fmt:formatDate value="${task.date}" pattern="dd MMM yyyy"/></p>
    <span class="lead">${fn:escapeXml(task.title)}</span>
    <c:forEach var="tag" items="${fn:escapeXml(task.tags)}">
    	<span class="badge badge-info">${fn:escapeXml(tag)}</span>
    </c:forEach>
    <p>${task.text}</p>
</div>