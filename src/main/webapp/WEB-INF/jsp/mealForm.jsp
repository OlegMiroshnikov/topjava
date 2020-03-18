<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<section>
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <hr>
    <h2><c:if test="${empty meal.id}"><spring:message code="meal.create"></spring:message></c:if></h2>
    <h2><c:if test="${!empty meal.id}"><spring:message code="meal.edit"></spring:message></c:if></h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}/meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.dateTime"></spring:message>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"></spring:message>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"></spring:message>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="meal.save"></spring:message></button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.cancel"></spring:message></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
