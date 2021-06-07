<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String[] url = request.getRequestURI().split("/");
String fileName = url[url.length-1].split(".jsp")[0];
%>
<!--[if IE]>
    <script src="../include/js/html5shiv.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="/static/include/css/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="/static/include/css/common.css">
<link rel="stylesheet" type="text/css" href="/static/include/css/tooltip.css">
<link rel="stylesheet" type="text/css" href="/static/include/css/confirm_table_view.css">

<link rel="stylesheet" type="text/css" href="/static/include/css/loading.css">


<script type="text/javascript" src="/static/include/js/jquery-1.12.4.min.js"></script>
<!-- <script type="text/javascript" src="/static/include/js/jquery-fakeform-0.5.min.js"></script> -->
<!-- <script type="text/javascript" src="/static/include/js/jquery.placeholder.min.js"></script> -->
<script type="text/javascript" src="/static/include/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/static/include/js/jquery.battatech.excelexport.js"></script>

<script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit" async defer></script>

<script type="text/javascript" src="/static/include/js/common.js"></script>
<script type="text/javascript" src="/static/include/js/utils.js"></script>

<script type="text/javascript" src="/static/include/js/page-common.js"></script>
<script type="text/javascript" src="/static/include/js/page/common/company-common.js"></script>
<script type="text/javascript" src="/static/include/js/page/common/layer-common.js"></script>
<script type="text/javascript" src="/static/include/js/page/common/service-common.js"></script>

<script type="text/javascript" src="/static/include/js/page/<%=url[url.length-2] %>/<%=fileName %>.js"></script>


<script type="text/javascript" src="/static/include/js/loading.js"></script>