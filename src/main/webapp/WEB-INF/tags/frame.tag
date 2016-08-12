<%@ tag description="Main JSP Frame Template" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--//用于内嵌的页面加载自身需要的资源 -->
<%@attribute name="linkResources" fragment="true" %>
<!--//用于内嵌的页面加载自身需要的JS -->
<%@attribute name="linkScripts" fragment="true" %>
<!--//页面的JS引用将会放在页面底部用以加快页面加载，被嵌入的子页面应该在该代码块内处理初始化任务 -->
<%@attribute name="pageDidLoad" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon" /> -->
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>" />
<title>
<spring:message code="site.title" />
</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-switch.min.css" rel="stylesheet">
<link href="css/styles.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">

<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="js/bootstrap.min.js"></script>
<!--//链接子页面脚本 -->
<jsp:invoke fragment="linkScripts" />

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->

<!--//子页面级资源应该有最高优先级，因此最后引入 -->
<jsp:invoke fragment="linkResources" />
</head>

<body>
    <input type="hidden" id="baseUrl" value="<%=basePath%>" />
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
                    <span class="sr-only">显示/隐藏菜单</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="admin">
                    <span><spring:message code="site.name.prefix" /></span> <spring:message code="site.name.suffix" />
                    <c:if test="${ not empty version }"> V${ version }</c:if>
                </a>
                <ul class="user-menu">
                    <li class="dropdown pull-right">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-user"></span> ${ current_user.mobile } <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="javascript:;"><span class="glyphicon glyphicon-user"></span>个人资料</a></li>
                            <li><a href="javascript:;"><span class="glyphicon glyphicon-cog"></span>设置</a></li>
                            <li><a href="admin/logout"><span class="glyphicon glyphicon-log-out"></span>退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div><!-- /.container-fluid -->
    </nav>
        
    <div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
        <form role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search">
            </div>
        </form>
        <ul class="nav menu">
            <li><a href=""><span class="glyphicon glyphicon-home"></span>网站前台</a></li>
            <li role="presentation" class="divider"></li>
            <li id="miSample">
                <a href="/admin"><span class="glyphicon glyphicon-th"></span>数据管理</a>
            </li>
            <li id="miUsers">
                <a href="/admin/users"><span class="glyphicon glyphicon-user"></span>用户管理</a>
            </li>
            <li role="presentation" class="divider"></li>
            <li id="miSettings">
                <a href="/admin/settings"><span class="glyphicon glyphicon-cog"></span>系统信息</a>
            </li>
        </ul>
        <div class="attribution"><a href="<spring:message code="site.homepage" />" target="_blank">
            <spring:message code="site.copyright" />
        </a></div>
    </div><!--/.sidebar-->
    <div id="contentContainer" class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <jsp:doBody />
    </div>
    <!--//初始化 -->
    <script src="js/custom/app.js"></script>
    <jsp:invoke fragment="pageDidLoad" />
</body>
</html>
