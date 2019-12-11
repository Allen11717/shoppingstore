<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>电子商务常用左侧导航菜单</title>

    <%--<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/styles1.css" />--%>

    <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
</head>
<body>
<div class="submenu">
    <h2>商品</h2>
    <div id="js-menu" class="submenulist">
        <ul class="pieces">
            <li>
                <div class="listbox hide">
                    <h3 class="future"><a></a></h3>
                    <dl class="procate">
                        <dt>商品分类</dt>
                        <dd><a></a></dd>
                    </dl>
                </div>
            </li>
        </ul>
    </div>
</div>

<script>
    $(function () {
        $.ajax({
            url:"selectAllP_type",
            type:"post",
            success:function(data){

                for(var i=0;i<data.length;i++){
                    var str="<li>\n" +
                        "                <div class=\"listbox hide\">\n" +
                        "                    <h3 class=\"future\"><a style='color: red'>"+data[i]+"</a></h3>\n" +
                        "                </div>\n" +
                        "            </li>"
                    $(".submenulist").prepend(str);
                }
            }
        })
    })

</script>







</body>