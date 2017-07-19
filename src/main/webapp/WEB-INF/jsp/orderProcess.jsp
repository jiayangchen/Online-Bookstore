<%--
  Created by IntelliJ IDEA.
  User: ChenJiayang
  Date: 2017/4/13
  Time: 15:15
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

    <title><spring:message code="orderprocess"/></title>

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
        <h1><spring:message code="orderdetails"/> <small><spring:message code="orderconfirm"/></small></h1>
    </div>
    <p class="lead"><spring:message code="ordercode"/>：${orderCode}</p>
    <p class="lead"><spring:message code="buyername"/>：${ordername} <spring:message code="buyeraddr"/>: ${orderaddress}</p>
    <p class="lead"><spring:message code="totalPrice"/>：￥ ${totalPrice}</p>
    <br>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><spring:message code="bookname"/></th>
                    <th><spring:message code="bookprice"/></th>
                    <th><spring:message code="bookquan"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${!empty orderlist}">
                    <c:forEach var="book" items="${orderlist}">
                        <tr>
                            <td>${book.bName}</td>
                            <td>${book.bPrice}</td>
                            <td>${book.bQuantity}</td>
                        </tr>
                    </c:forEach>
                </c:if>

                <c:if test="${empty orderlist}">
                    Empty
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
    <br><br><br>
    <nav style="text-align: right">
    <a class="btn btn-primary" href="<c:url value="/updateOrderStatus"/>"><spring:message code="pay"/></a>
    <a class="btn btn-warning" href="<c:url value="/myProductPage?pageNow=1"/>"><spring:message code="back"/></a>
    </nav>
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

