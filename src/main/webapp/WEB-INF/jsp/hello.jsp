<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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

    <title>书店首页</title>

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

    <style>
        img.book-img {
            display: block;
            float: left;

            margin-right: 5px;
            margin-bottom: 2px;

            width: auto;
            height: 140px;
        }
    </style>
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
            </c:if>
        </div>
            <div id="navbar" class="navbar-collapse collapse">
                <form class="navbar-form navbar-right" action="<c:url value="/logout"/>" method="post">
                    <button type="submit" class="btn btn-danger"><spring:message code="logout"/></button>
                </form>
                <c:if test="${role == 'user'}">
                <form class="navbar-form navbar-right" action="<c:url value="/viewCart"/>" method="post">
                    <button type="submit" class="btn btn-success"><spring:message code="viewcart"/></button>
                </form>
                </c:if>
                <c:if test="${role == 'user'}">
                    <form class="navbar-form navbar-right" action="<c:url value="/userCenter"/>" method="post">
                        <button type="submit" class="btn btn-info"><spring:message code="userCenter"/></button>
                    </form>
                </c:if>
                <form class="navbar-form navbar-right" action="<c:url value="/luceneSearch"/>" method="post">
                    <div class="form-group">
                        <input name="target" type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default"><spring:message code="search"/></button>
                </form>

                <form class="navbar-form navbar-right">
                <div class="btn-group">
                    <button type="button" class="btn btn-default"><spring:message code="category"/></button>
                    <button type="button" class="btn btn-default dropdown-toggle"
                            data-toggle="dropdown">
                        <span class="caret"></span>
                        <span class="sr-only">切换下拉菜单</span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#"><spring:message code="literature"/></a></li>
                        <li><a href="#"><spring:message code="science"/></a></li>
                        <li><a href="#"><spring:message code="novel"/></a></li>
                        <li><a href="#"><spring:message code="fiction"/></a></li>
                        <li><a href="#"><spring:message code="IT"/></a></li>
                        <li><a href="#"><spring:message code="biography"/></a></li>
                        <li class="divider"></li>
                        <li><a href="#"><spring:message code="all"/></a></li>
                    </ul>
                </div>
                </form>
            </div>
    </div>
</nav>

<!-- Main jumbotron for a primary marketing message or call to action -->
<%--<div class="jumbotron">
    &lt;%&ndash;<div class="container">
        <h1><spring:message code="bookstore"/></h1>
        <p><spring:message code="storeinfo"/></p>

        <c:if test="${role == 'admin'}">
        <form action="<c:url value="/perCenter"/>" method="post">
            <p><button type="submit" class="btn btn-primary btn-lg"><spring:message code="percenter"/> &raquo;</button></p>
        </form>
        </c:if>
    </div>&ndash;%&gt;
</div>--%>

<br><br><br><br>
<br><br>
<header>
    <div class="container">
        <div class="row">
            <div class="col-xs-10 col-xs-offset-1 col-md-10 col-md-offset-1 herobanner_content">
                <div class="col-md-6">
                    <h1>Welcome to Online Bookstore</h1>
                    <p>OKR, weekly check-ins and daily stand-ups to help you build and maintain engaged, aligned and productive teams no matter where they are.</p>

                </div>
                <div class="col-md-6">
                    <img class="img-responsive" src="https://www.newsteer.com/images/herobanner-new.png" alt="One on One Check-ins to know the pulse and engagement of your team">
                </div>
            </div>
        </div>
    </div>
</header>

<div class="container">
    <!-- Example row of columns -->
    <div class="row">
    <c:if test="${!empty bookList}">
        <c:forEach var="book" items="${bookList}" varStatus="status">
            <c:if test="${status.index % 3 == 0}">
                </div><br>
                <div class="row">
            </c:if>
            <div class="col-md-4">
                <h2>${book.bName}</h2>
                <img class="img-rounded book-img" src="${book.bCover}">
                <p> Price: <font color="#FF0000">￥ ${book.bPrice} </font><br>Stock: ${book.bQuantity}<br>Producer: <b>CMB</b><br>${book.bDiscr}</p>
                <div>
                <c:if test="${role == 'user'}">

                    <form style="display:inline-block;" action="<c:url value="/addCart"/>" method="post">
                        <input type="hidden" value="${book.bid}" name="addtocartBtn">
                        <button class="btn btn-default" ><spring:message code="addCart"/> &raquo;</button>
                    </form>
                    <button class="btn btn-info btn-book-view" data-bid="${book.bid}"><spring:message code="viewInfo"/> &raquo;</button>

                </c:if>
                <c:if test="${role == 'admin'}">
                    <button class="btn btn-info btn-book-view" data-bid="${book.bid}"><spring:message code="viewInfo"/> &raquo;</button>
                </c:if>
                <c:if test="${role == 'manager'}">
                    <button class="btn btn-info btn-book-view" data-bid="${book.bid}"><spring:message code="viewInfo"/> &raquo;</button>
                </c:if>
                </div>
                <div class="clearfix"></div>
            </div>
        </c:forEach>
    </c:if>
    </div>
    <hr>
    <footer>
        <nav style="text-align: right">
        <ul class="pagination">
            <li><a href="#">&laquo;</a></li>
            <li class="active"><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">&raquo;</a></li>
        </ul>
        </nav>
        <p>&copy; 2016 Company, Inc.</p>
    </footer>
</div> <!-- /container -->

<span id="msg" style="color:#4a80ff;"></span>

<div class="modal fade" id="bookInfoModal" tabindex="-1" role="dialog" aria-labelledby="bookInfoModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Book Info</h4>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>

<script type="text/javascript">

    //jQuery-ajax

    $('.btn-book-view').click(function () {
        var btn = $(this);
        var bookId = parseInt(btn.data('bid'));

        var url = "<c:url value="/viewInfo"/>";
        $.post(url, { addtocartBtn: bookId}, function (data) {
            $('#bookInfoModal .modal-body').text(JSON.parse(data));
            $('#bookInfoModal').modal('show');
        });
    })

</script>
</body>
</html>
