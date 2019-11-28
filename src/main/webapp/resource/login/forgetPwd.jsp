<%--
  Created by IntelliJ IDEA.
  User: 60483
  Date: 2019/11/26
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>找回密码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

</head>
<body>
    <h3>找回密码</h3>
    <input type="text" id="username3" placeholder="用户名"/><br/>
    <input type="text" id="password3"placeholder="密码"/><br/>
    <input type="button" id="btn" value="确认"/>
    <div id="msg3"></div>


    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                if($("#username3").val()!=''&&$("#password3").val()!='') {
                    $.ajax({
                        url: "modify",
                        Type: "post",
                        data: {
                            "username": $("#username3").val(),
                            "password": $("#password3").val()
                        }, success: function (data) {
                            if (data == "none") {
                                $("#msg3").html("用户名不存在")
                            } else if (data = "ok") {
                                $("#msg3").html("修改成功")
                            } else {
                                $("#msg3").html("修改失败")
                            }
                        }
                    })
                }else{
                    $("#msg3").html("用户名和密码不能为空!")
                }
            })
        })
    </script>

</body>
</html>
