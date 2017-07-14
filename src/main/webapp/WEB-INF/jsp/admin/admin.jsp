<%--
  Created by IntelliJ IDEA.
  User: ChenJiayang
  Date: 2017/3/18
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: ChenJiayang
  Date: 2017/3/16
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title><spring:message code="admincenter"/></title>

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="sticky-footer.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<!-- Begin page content -->
<div class="container">
    <div class="page-header">
        <h1><spring:message code="admincenter"/></h1>
    </div>
    <nav style="text-align: right">
        <a class="btn btn-warning" href="<c:url value="/logout"/>"><spring:message code="logout"/></a>
    </nav><br>
    <p class="lead"><spring:message code="adduser"/></p>
    <form action="<c:url value="/addUser"/>" method="post">
    <div class="row">
        <div class="col-md-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><spring:message code="uname"/></th>
                    <th><spring:message code="pass"/></th>
                    <th><spring:message code="role"/></th>
                    <th><spring:message code="sex"/></th>
                    <th><spring:message code="address"/></th>
                    <th><spring:message code="phone"/></th>
                </tr>
                </thead>
                <tbody>
                        <tr>
                            <td><input type="text" name="name" class="form-control" placeholder="<spring:message code="uname"/>"></td>
                            <td><input type="text" name="password" class="form-control" placeholder="<spring:message code="pass"/>"></td>
                            <td>
                                <input type="text" name="role" class="form-control" placeholder="<spring:message code="role"/>">
                            </td>
                            <td>
                                <input type="text" name="sex" class="form-control" placeholder="<spring:message code="sex"/>">
                            </td>
                            <td><input type="text" name="address" class="form-control" placeholder="<spring:message code="address"/>"></td>
                            <td><input type="text" name="phone" class="form-control" placeholder="<spring:message code="phone"/>"></td>
                        </tr>
                </tbody>
            </table>
        </div>
    </div>
    <nav style="text-align: right">
        <button type="submit" class="btn btn-primary"><spring:message code="adduser"/></button>
    </nav>
    </form>


    <p class="lead"><spring:message code="usermana"/></p>
    <form action="<c:url value="/updateUser"/>" method="post">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th><spring:message code="uname"/></th>
                        <th><spring:message code="pass"/></th>
                        <th><spring:message code="role"/></th>
                        <th><spring:message code="sex"/></th>
                        <th><spring:message code="address"/></th>
                        <th><spring:message code="phone"/></th>
                        <th><spring:message code="operation"/></th>
                        <th><spring:message code="operation"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${!empty userList}">
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.uName}</td>
                                <td>${user.uPassword}</td>
                                <td>
                                    <c:if test="${user.rid == 1}">
                                        <input type="text" name="role" class="form-control" placeholder="User">
                                    </c:if>
                                    <c:if test="${user.rid == 2}">
                                        <input type="text" name="role" class="form-control" placeholder="Admin">
                                    </c:if>
                                    <c:if test="${user.rid == 3}">
                                        <input type="text" name="role" class="form-control" placeholder="Producer">
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${user.sex == 1}">
                                        <spring:message code="man"/>
                                    </c:if>
                                    <c:if test="${user.sex == 0}">
                                        <spring:message code="woman"/>
                                    </c:if>
                                </td>
                                <td>${user.address}</td>
                                <td>${user.phone}</td>
                                <td>
                                    <button value="${user.uName}" name="updateUser" type="submit" class="btn btn-success"><spring:message code="save"/></button>
                                </td>
                                <td>
                                    <form action="<c:url value="/deleteUser"/>" method="post">
                                    <button value="${user.uName}" name="deleteUser" type="submit" class="btn btn-danger"><spring:message code="delete"/></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    <c:if test="${empty userList}">
                        Empty
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </form>

</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

