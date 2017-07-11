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

    <title>Lucene Search Result</title>

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
        <h1>Search Result</h1>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Order Code</th>
                    <th>Order Price</th>
                    <th>Order Create Time</th>
                    <th>Order Status</th>
                    <th>Order Details</th>
                    <th>Operation</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${!empty result}">
                    <c:forEach var="order" items="${result}">
                        <tr>
                            <td>${order.ocode}</td>
                            <td>${order.o_amount}</td>
                            <td>${order.o_create_time}</td>
                            <td>
                                <c:if test="${order.o_status == 1}">
                                    Submitted
                                </c:if>
                                <c:if test="${order.o_status == 2}">
                                    Paid
                                </c:if>
                            </td>
                            <td>
                                <button type="button" class="btn btn-default">View Details</button>
                            </td>
                            <td>
                                <c:if test="${order.o_status == 1}">
                                    <button type="submit" class="btn btn-primary btn-order-cancel" data-bid="${order.ocode}">Cancel Order</button>
                                </c:if>
                                <c:if test="${order.o_status == 2}">
                                    <button type="button" class="btn btn-primary" disabled="disabled" data-bid="${order.ocode}">Cancel Order</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>

                <c:if test="${empty result}">
                    Empty
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
    <nav style="text-align: right">
        <a class="btn btn-warning" href="<c:url value="/back"/>">Back To MainPage</a>
    </nav><br><br>
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
