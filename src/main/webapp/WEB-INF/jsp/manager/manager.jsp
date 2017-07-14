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

    <title>Producer Center</title>

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
        <h1><spring:message code="procenter"/></h1>
    </div>
    <nav style="text-align: right">
        <a class="btn btn-default" href="<c:url value="/orderManagement"/>"><spring:message code="ordermanage"/></a>
        <a class="btn btn-primary" href="<c:url value="/productManagement"/>"><spring:message code="promanage"/></a>
        <a class="btn btn-warning" href="<c:url value="/logout"/>"><spring:message code="logout"/></a>
    </nav><br>

    <c:if test="${!empty orderList}">
    <p class="lead"><spring:message code="ordermanage"/></p>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th><spring:message code="ordercode"/></th>
                        <th><spring:message code="userid"/></th>
                        <th><spring:message code="proid"/></th>
                        <th><spring:message code="ordertime"/></th>
                        <th><spring:message code="orderstatus"/></th>
                        <th><spring:message code="orderprice"/></th>
                        <th><spring:message code="operation"/></th>
                        <th><spring:message code="operation"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td>${order.ocode}</td>
                        <td>${order.ouid}</td>
                        <td>${order.opid}</td>
                        <td>${order.o_create_time}</td>
                        <td>
                            <c:if test="${order.o_status == 0}">
                                <font color="#EE2C2C"><b><spring:message code="refused"/></b></font>
                            </c:if>
                            <c:if test="${order.o_status == 1}">
                                <font color="#EEC900"><b><spring:message code="submitted"/></b></font>
                            </c:if>
                            <c:if test="${order.o_status == 2}">
                                <font color="#4876FF"><b><spring:message code="paid"/></b></font>
                            </c:if>
                            <c:if test="${order.o_status == 3}">
                                <font color="#32CD32"><b><spring:message code="accepted"/></b></font>
                            </c:if>
                            <c:if test="${order.o_status == 4}">
                                <font color="#969696"><b><spring:message code="canceled"/></b></font>
                            </c:if>
                        </td>
                        <td>${order.o_amount}</td>
                        <td>
                            <c:if test="${order.o_status != 2}">
                                <button value="${order.ocode}" name="acceptOrder" type="submit" class="btn btn-success" disabled="disabled"><spring:message code="accept"/></button>
                            </c:if>
                            <c:if test="${order.o_status == 2}">
                                <form action="<c:url value="/acceptOrder"/>" method="post">
                                    <button value="${order.ocode}" name="acceptOrder" type="submit" class="btn btn-success"><spring:message code="accept"/></button>
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${order.o_status != 2}">
                                <button value="${order.ocode}" name="deleteOrder" type="submit" class="btn btn-danger" disabled="disabled"><spring:message code="refuse"/></button>
                            </c:if>
                            <c:if test="${order.o_status == 2}">
                                <form action="<c:url value="/deleteOrder"/>" method="post">
                                    <button value="${order.ocode}" name="deleteOrder" type="submit" class="btn btn-danger"><spring:message code="refuse"/></button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

    <c:if test="${!empty productList}">
        <p class="lead"><spring:message code="promanage"/></p>
        <nav style="text-align: right">
            <a class="btn btn-default" href="<c:url value="/productManagement?type=zh"/>">中文</a>
            <a class="btn btn-primary" href="<c:url value="/productManagement?type=en"/>">English</a>
        </nav><br>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Index</th>
                        <th><spring:message code="bookname"/></th>
                        <th><spring:message code="bookauthor"/></th>
                        <th><spring:message code="category"/></th>
                        <th><spring:message code="bookstock"/></th>
                        <th><spring:message code="bookprice"/></th>
                        <th><spring:message code="bookdescr"/></th>
                        <th><spring:message code="bookstatus"/></th>
                        <th><spring:message code="operation"/></th>
                        <th><spring:message code="operation"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="book" items="${productList}" varStatus="status">
                    <form action="<c:url value="/updateBook?isEnglish=${book.isEnglish}"/>" method="post">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>
                                <input type="text" name="bname" class="form-control" placeholder="${book.bName}">
                            </td>
                            <td>
                                <input type="text" name="bauthor" class="form-control" placeholder="${book.bAuthor}">
                            </td>
                            <td>
                                <input type="text" name="bcate" class="form-control" placeholder="${book.bCategory}">
                            </td>
                            <td>
                                <input type="text" name="bquan" class="form-control" placeholder="${book.bQuantity}">
                            </td>
                            <td>
                                <input type="text" name="bprice" class="form-control" placeholder="${book.bPrice}">
                            </td>
                            <td>
                                <input type="text" name="bdescr" class="form-control" placeholder="${book.bDiscr.substring(0,10)}">
                            </td>
                            <td>
                                <c:if test="${book.isSold == 0}">
                                    <button value="${book.bid}" name="isSold" type="submit" class="btn btn-primary"><spring:message code="market"/></button>
                                </c:if>
                                <c:if test="${book.isSold == 1}">
                                    <button value="${book.bid}" name="isSold" type="submit" class="btn btn-warning"><spring:message code="nomarket"/></button>
                                </c:if>
                            </td>
                            <td><button value="${book.bid}" name="upbookid" type="submit" class="btn btn-success"><spring:message code="save"/></button></td>
                            <td><button value="${book.bid}" name="upbookid" type="submit" class="btn btn-danger"><spring:message code="delete"/></button></td>
                        </tr>
                    </form>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
