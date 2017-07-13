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
        <h1>Producer Center</h1>
    </div>
    <nav style="text-align: right">
        <a class="btn btn-default" href="<c:url value="/orderManagement"/>">Order Management</a>
        <a class="btn btn-primary" href="<c:url value="/productManagement"/>">Product Management</a>
        <a class="btn btn-warning" href="<c:url value="/logout"/>">Logout</a>
    </nav><br>

    <c:if test="${!empty orderList}">
    <p class="lead">Order Managerment</p>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Order Code</th>
                        <th>User Id</th>
                        <th>Producer Id</th>
                        <th>Create Time</th>
                        <th>Status</th>
                        <th>Price</th>
                        <th>Accept</th>
                        <th>Refuse</th>
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
                                <font color="#EE2C2C"><b>Refused</b></font>
                            </c:if>
                            <c:if test="${order.o_status == 1}">
                                <font color="#EEC900"><b>Submitted</b></font>
                            </c:if>
                            <c:if test="${order.o_status == 2}">
                                <font color="#4876FF"><b>Paid</b></font>
                            </c:if>
                            <c:if test="${order.o_status == 3}">
                                <font color="#32CD32"><b>Accepted</b></font>
                            </c:if>
                            <c:if test="${order.o_status == 4}">
                                <font color="#969696"><b>Canceled</b></font>
                            </c:if>
                        </td>
                        <td>${order.o_amount}</td>
                        <td>
                            <c:if test="${order.o_status != 2}">
                                <button value="${order.ocode}" name="acceptOrder" type="submit" class="btn btn-success" disabled="disabled">Accept</button>
                            </c:if>
                            <c:if test="${order.o_status == 2}">
                                <form action="<c:url value="/acceptOrder"/>" method="post">
                                    <button value="${order.ocode}" name="acceptOrder" type="submit" class="btn btn-success">Accept</button>
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${order.o_status != 2}">
                                <button value="${order.ocode}" name="deleteOrder" type="submit" class="btn btn-danger" disabled="disabled">Refuse</button>
                            </c:if>
                            <c:if test="${order.o_status == 2}">
                                <form action="<c:url value="/deleteOrder"/>" method="post">
                                    <button value="${order.ocode}" name="deleteOrder" type="submit" class="btn btn-danger">Refuse</button>
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
        <p class="lead">Product Managerment</p>
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
                        <th>Book Name</th>
                        <th>Author</th>
                        <th>Category</th>
                        <th>Stock</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>isSold</th>
                        <th>Save</th>
                        <th>Delete</th>
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
                                    <button value="${book.bid}" name="isSold" type="submit" class="btn btn-primary">Market</button>
                                </c:if>
                                <c:if test="${book.isSold == 1}">
                                    <button value="${book.bid}" name="isSold" type="submit" class="btn btn-warning">Withdraw</button>
                                </c:if>
                            </td>
                            <td><button value="${book.bid}" name="upbookid" type="submit" class="btn btn-success">Save</button></td>
                            <td><button value="${book.bid}" name="upbookid" type="submit" class="btn btn-danger">Delete</button></td>
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
