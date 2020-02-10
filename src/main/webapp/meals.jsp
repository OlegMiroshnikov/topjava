<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link rel="stylesheet" href="table.css" type="text/css">
    <title>Meals</title>
</head>

<body>

<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
<c:if test="${!empty meals}">
    <table class="tg">
        <tr>
            <th width="220">Дата/время</th>
            <th width="520">Описание</th>
            <th width="80">Калории</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <tr bgcolor="${meal.excess ? 'red' : 'greenyellow'}">
                <td>
                    <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Edit</a></td>
                <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br/>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>
