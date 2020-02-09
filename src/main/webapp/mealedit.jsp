<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <link rel="stylesheet" href="form.css" type="text/css">
    <title>Meal data</title>
</head>
<body>

<h3><a href="<c:url value='/meals'/>">Back to meals list</a></h3>
<hr>
<h2>Meals details</h2>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>

<form class="fm" method="POST" action="meals">
    ID : <input
        type="text" readonly="readonly" name="id"
        value="<c:out value="${meal.id}"/>"/> <br/>
    Дата/время : <input
        type="text" name="dateTime"
        value = "<fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                       type="both"/>
        <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>" /> <br/>
    Описание : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Калории : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <br/>
    <input type="submit" value="OK"/>
</form>
</body>
</html>