<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title><spring:message code="greet"/></title>

    <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <style type="text/css">
        img{
            margin:0 auto;
        }
    </style>
</head>

<body>
<%--<a href="<c:url value="/welcome"/>"><h1>Hello World</h1></a>--%>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><spring:message code="bookstore"/></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <form class="navbar-form navbar-right">
                <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    语言(Language) <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="?langType=zh">中文</a></li>
                    <li><a href="?langType=en">English</a></li>
                </ul>
                </div>
            </form>
        </div>
    </div>
</nav>

<div class="container">

    <form class="form-signin" action="<c:url value="/welcome"/>" method="post">
        <h2 class="form-signin-heading"><spring:message code="greet"/></h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="text" name="username" id="inputEmail" class="form-control" placeholder="<spring:message code="email"/>" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="<spring:message code="password"/>" required>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login"/></button>
    </form>
    <%--<form action="<c:url value="/logout"/>" method="post">
        <button class="btn btn-lg btn-danger btn-block" type="submit"><spring:message code="reset"/></button>
    </form>--%>

    <%--<form action="<c:url value="/testJms"/>" method="post">
        <button class="btn btn-lg btn-danger btn-block" type="submit">Jms</button>
    </form>--%>

</div> <!-- /container -->

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>



