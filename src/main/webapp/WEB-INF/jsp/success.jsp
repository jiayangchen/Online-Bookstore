<%--
  Created by IntelliJ IDEA.
  User: ChenJiayang
  Date: 2017/3/16
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
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

    <title></title>

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

    <script language="javascript" type="application/javascript">
        var pwd = document.getElementById("paypwd").value;

    </script>
</head>

<body>

<!-- Begin page content -->
<div class="container">
    <div class="page-header">
        <h1>Payment</h1>
    </div>
    <p class="lead">Please input your payment password here.</p>
    <div id="navbar" class="navbar-collapse collapse">
        <form class="navbar-form navbar-left" action="<c:url value="/confirmpay"/>" method="post">
            <div class="form-group">
                <input id="paypwd" type="password" placeholder="Password" class="form-control" name="paypwd">
            </div>
            <button type="submit" class="btn btn-success" onclick="encrypt">Confirm</button>
        </form>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="text-muted">Your Financial Information will be carefully protected.</p>
    </div>
</footer>


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

