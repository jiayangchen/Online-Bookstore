<%--
  Created by IntelliJ IDEA.
  User: ChenJiayang
  Date: 2017/3/16
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Book List</title>
</head>
    <body>
        <c:if test="${!empty bookList}">
            <c:forEach var="book" items="${bookList}" varStatus="i">
                序号：${i.index} &nbsp;&nbsp;
                书名：${book.bName} &nbsp;&nbsp;
                作者：${book.bAuthor} &nbsp;&nbsp;
                价格：${book.bPrice} &nbsp;&nbsp;<br>
            </c:forEach>
        </c:if>
    </body>
</html>
