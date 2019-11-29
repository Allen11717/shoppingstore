<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head id="hh">
    <base href="<%=basePath%>">
    <title></title>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="<%=basePath%>resource/js/modernizr.custom.js"></script>
    <link href="http://cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/component.css" />


   <style>

       body{
           background: #2A2B30;
       }

   </style>

</head>
<body >
	<!-- Compare basket -->
<div class="compare-basket">
    <button class="action action--button action--compare"><i class="fa fa-check"></i><span class="action__text">Compare</span></button>
</div>
<!-- Main view -->
<div class="view">
    <!-- Product grid -->
    <section class="grid">
        <!-- Products -->
        <div class="product">
            <div class="product__info">
                <img class="product__image" src="<%=basePath%>resource/images/1.png" alt="Product 1" />
                <h3 class="product__title">商品名</h3>
                <span class="product__region extra highlight">商品介绍</span>
                <span class="product__varietal extra highlight">商品规格</span>
                <span class="product__price highlight">价格</span>
                <button class="action action--button action--buy"><i class="fa fa-shopping-cart"></i><span class="action__text cd-add-to-cart">Add to cart</span></button>
            </div>
            <label class="action action--compare-add"><input class="check-hidden" type="checkbox" /><i class="fa fa-plus"></i><i class="fa fa-check"></i><span class="action__text action__text--invisible">Add to compare</span></label>
        </div>


        <div class="product">
            <div class="product__info">
                <img class="product__image" src="<%=basePath%>resource/images/2.png" alt="Product 2" />
                <h3 class="product__title">商品名</h3>
                <span class="product__region extra highlight">商品介绍</span>
                <span class="product__varietal extra highlight">商品规格</span>
                <span class="product__price highlight">价格</span>
                <button class="action action--button action--buy"><i class="fa fa-shopping-cart"></i><span class="action__text cd-add-to-cart">Add to cart</span></button>
            </div>
            <label class="action action--compare-add"><input class="check-hidden" type="checkbox" /><i class="fa fa-plus"></i><i class="fa fa-check"></i><span class="action__text action__text--invisible">Add to compare</span></label>
        </div>

    </section>
</div><!-- /view -->





<!-- product compare wrapper -->
<section class="compare">
    <button class="action action--close"><i class="fa fa-remove"></i><span class="action__text action__text--invisible">Close comparison overlay</span></button>
</section>




    <script src="<%=basePath%>resource/js/classie.js"></script>
    <script src="<%=basePath%>resource/js/main.js"></script>

<script>
    $(function(){

  $.ajax({
   url:"selectAllProductsByP_type",
      type:"post",
      data:{
      "p_type":getQueryString("p_type")
      },
      success:function(data){

       // for(var i=0;i<data.length;i++){
       // var str="<div  class='sHoverItem'>" +
       //     "    <img  src='"+data[i].pic+"'>" +
       //     "    <span  class='sIntro'>" +
       //     "<h2>"+data[i].pName+"</h2>" +
       //     "<p>"+data[i].intro+"</p>" +
       //     "<p>$"+data[i].price+"</p>"+
       //     "<button >立即购买</button>" +
       //     "<button pid='"+data[i].pId+"' class='addCart'>加入购物车</button>" +
       //     "</span>" +
       //     " </div>";
       //     $(".container").append(str);
       // }


      }
  });


  $(".container").on("click",".addCart",function(){
    alert($(this).attr("pid"));

  });


    });



    function getQueryString(name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)
            return  decodeURI(r[2]);
        return null;
    }

    function loadjscssfile(filename, filetype) {
        if (filetype == "js") { //判定文件类型
            var fileref = document.createElement('script');//创建标签
            fileref.setAttribute("type", "text/javascript");//定义属性type的值为text/javascript
            fileref.setAttribute("src", filename);//文件的地址
        }
        else if (filetype == "css") { //判定文件类型
            var fileref = document.createElement("link");
            fileref.setAttribute("rel", "stylesheet");
            fileref.setAttribute("type", "text/css");
            fileref.setAttribute("href", filename);
        }
        if (typeof fileref != "undefined")
            document.getElementsByTagName("head")[0].appendChild(fileref);
    }



</script>


</body>
</html>
