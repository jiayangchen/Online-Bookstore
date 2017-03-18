<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <title>Jumbotron Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="jumbotron.css" rel="stylesheet">

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

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <c:if test="${! empty username}">
                <a class="navbar-brand" href="#">Hello ${username}</a>

        </div>
            <div id="navbar" class="navbar-collapse collapse">
                <form class="navbar-form navbar-right" action="<c:url value="/viewCart"/>" method="post">
                    <button type="submit" class="btn btn-success">View Cart</button>
                </form>
                <form class="navbar-form navbar-right" action="<c:url value="/logout"/>" method="post">
                    <button type="submit" class="btn btn-danger">Log out</button>
                </form>
            </div>
        </c:if>
    </div>
</nav>

<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">
        <h1>BookStore</h1>
        <p>This is an online bookstore. You can buy whatever you like here. </p>
        <form action="<c:url value="/perCenter"/>" method="post">
            <p><button type="submit" class="btn btn-primary btn-lg">Personal Center &raquo;</button></p>
        </form>
    </div>
</div>

<div class="container">
    <!-- Example row of columns -->
    <div class="row">
    <c:if test="${!empty bookList}">
        <c:forEach var="book" items="${bookList}">
            <div class="col-md-4">
                <h2>${book.bName}</h2>
                <p>${book.bDiscr}</p>
                <form action="<c:url value="/addCart"/>" method="post">
                    <input type="hidden" value="${book.bid}" name="addtocartBtn">
                    <button class="btn btn-default" >AddToCart &raquo;</button>
                </form>
            </div>
        </c:forEach>
    </c:if>
    </div>
    <hr>
    <footer>
        <p>&copy; 2016 Company, Inc.</p>
    </footer>
</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
