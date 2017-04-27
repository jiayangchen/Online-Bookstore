<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ChenJiayang
  Date: 2017/4/16
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ChatRoom</title>
    <script src="http://lib.sinaapp.com/js/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var socket =null;
        $(function(){
            function parseObj(strData){//转换对象
                return (new Function( "return " + strData ))();
            };
            //创建socket对象
            socket = new WebSocket("ws://"+ window.location.host+"/${pageContext.request.contextPath}/game");
            //连接创建后调用
            var sessionValue= $("#hdnSession").data('value');
            socket.onopen = function() {

                $("#showMsg").append("用户"+sessionValue + "进入聊天室...<br/>");
            };
            //接收到服务器消息后调用
            socket.onmessage = function(message) {
                var data=parseObj(message.data);
                if(data.type=="message"){
                    $("#showMsg").append("<span style='display:block'>"+data.text+"</span>");
                }else if(data.type=="background"){
                    $("#showMsg").append("<span style='display:block'>系统改变背景地址,背景地址是:"+data.text+"</span>");
                    $("body").css("background","url("+data.text+")");
                }
            };
            //关闭连接的时候调用
            socket.onclose = function(){
                alert("close");
            };
            //出错时调用
            socket.onerror = function() {
                alert("error");
            };
            $("#sendButton").click(function() {
                var time = new Date();

                socket.send(time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds() + " "
                    + sessionValue + ":" + $("#msg").val());
            });
            $("#abcde").click(function(){
                $.post("${pageContext.request.contextPath}/backgroundimg");
            });
        });
    </script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>

<div class="page-header">
    <h1><%
        HttpSession s = request.getSession();
    %><%=s.getAttribute("sess_username")%>'s ChatRoom</h1>
</div>

<div id="showMsg" style="border: 1px solid; width: 500px; height: 400px; overflow: auto;"></div>
<br>

<div>

    <input type="hidden" id="hdnSession" data-value=<%=s.getAttribute("sess_username")%>/>
    <div class="input-group">
    <input type="text" id="msg"/>
    &nbsp;&nbsp;
    <%--<input type="button" id="sendButton" value="发送" />--%>
    <a type="button" id="sendButton" class="btn btn-info" >Send</a>&nbsp;&nbsp;
    <a class="btn btn-warning" href="<c:url value="/back"/>">Back</a>
    </div>
</div>

</body>
</html>
