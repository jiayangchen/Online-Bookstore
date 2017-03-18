<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>用户信息列表</title>
</head>
<body>
    <c:if test="${!empty userList}">
        <c:forEach var="user" items="${userList}">
            姓名：${user.uName} &nbsp;&nbsp;密码：${user.uPassword} &nbsp;&nbsp;<br>
        </c:forEach>
    </c:if>

    TestCase: ${testitem}
</body>
</html>
