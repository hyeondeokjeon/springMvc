<%--
  Created by IntelliJ IDEA.
  User: hyeondeok
  Date: 2018. 3. 5.
  Time: 오후 8:18
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User List</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>email</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%-- 처음--%>
<c:if test="${currentPage > 1}">
    <a href="/db/read?page=1&count=${count}">[처음]</a>
</c:if>
<%--이전--%>
<c:if test="${currentPage > 1}">
    <a href="/db/read?page=${currentPage - 1}&count=${count}">[이전]</a>
</c:if>
<%--범위 표시--%>
<c:forEach begin="${startPage}" end="${endPage}" varStatus="pageNum">
    <span>
    <c:choose>
        <c:when test="${currentPage == pageNum.index}">
            ${pageNum.index}
        </c:when>

        <c:when test="${pageNum.index >= totalPage}"/>

        <c:otherwise>
            <a href="/db/read?page=${pageNum.index}&count=${count}">${pageNum.index}</a>
        </c:otherwise>
    </c:choose>
    </span>
</c:forEach>
<%--다음--%>
<c:if test="${currentPage < totalPage}">
    <a href="/db/read?page=${currentPage + 1}&count=${count}">[다음]</a>
</c:if>
<%--끝--%>
<c:if test="${currentPage < totalPage}">
    <a href="/db/read?page=${totalPage}&count=${count}">[ 끝 ]</a>
</c:if>
</body>
</html>
