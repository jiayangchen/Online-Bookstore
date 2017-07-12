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
        <a class="btn btn-warning" href="<c:url value="/logout"/>">Logout</a>
    </nav><br>
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
                    <c:if test="${!empty orderList}">
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
                        </td>
                        <td>${order.o_amount}</td>
                        <td>
                            <c:if test="${order.o_status == 1}">
                                <button value="${order.ocode}" name="acceptOrder" type="submit" class="btn btn-success" disabled="disabled">Accept</button>
                            </c:if>
                            <c:if test="${order.o_status != 1}">
                                <form action="<c:url value="/acceptOrder"/>" method="post">
                                    <button value="${order.ocode}" name="acceptOrder" type="submit" class="btn btn-success">Accept</button>
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${order.o_status == 1}">
                                <button value="${order.ocode}" name="deleteOrder" type="submit" class="btn btn-danger" disabled="disabled">Refuse</button>
                            </c:if>
                            <c:if test="${order.o_status != 1}">
                                <form action="<c:url value="/deleteOrder"/>" method="post">
                                    <button value="${order.ocode}" name="deleteOrder" type="submit" class="btn btn-danger">Refuse</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                    </c:forEach>
                    </c:if>

                    <c:if test="${empty orderList}">
                        Empty
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>

   <%-- <p class="lead">User Management</p>
    <form action="<c:url value="/updateUser"/>" method="post">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>UserName</th>
                        <th>Password</th>
                        <th>Role</th>
                        <th>Sex</th>
                        <th>Address</th>
                        <th>Phone Number</th>
                        <th>Operation</th>
                        <th>Operation</th>
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
                                        Man
                                    </c:if>
                                    <c:if test="${user.sex == 0}">
                                        Woman
                                    </c:if>
                                </td>
                                <td>${user.address}</td>
                                <td>${user.phone}</td>
                                <td>
                                    <button value="${user.uName}" name="updateUser" type="submit" class="btn btn-success">Save</button>
                                </td>
                                <td>
                                    <form action="<c:url value="/deleteUser"/>" method="post">
                                        <button value="${user.uName}" name="deleteUser" type="submit" class="btn btn-danger">Delete</button>
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
    <nav style="text-align: right">
        <a class="btn btn-warning" href="<c:url value="/logout"/>">Logout</a>
    </nav><br><br><br>--%>
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
