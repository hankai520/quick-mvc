<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>" />
<title><spring:message code="site.title" /> | 登录</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/datepicker3.css" rel="stylesheet">
<link href="css/styles.css" rel="stylesheet">

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->

</head>

<body>
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading"><spring:message code="site.title" /></div>
                <div class="panel-body">
                    <form:form method="post" action="admin/login" modelAttribute="user" onsubmit="return verify();">
                        <fieldset>
                            <div class="form-group">
                                <form:input path="loginId" cssClass="form-control" placeholder="手机号" maxlength="15"/>
                                <form:errors path="loginId" cssClass="field-error"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:password path="password" cssClass="form-control" placeholder="密码" maxlength="50"/>
                                <form:errors path="password" cssClass="field-error"></form:errors>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <form:checkbox path="remember" />自动登录
                                </label>
                            </div>
                            <button type="submit" class="btn btn-primary">登录</button>
                        </fieldset>
                    </form:form>
                </div>
            </div>
        </div><!-- /.col-->
    </div><!-- /.row -->    

    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/crypto-js.js"></script>
    <script type="text/javascript">
    function verify() {
        var loginIdControl = $('#loginId');
        var pwdControl = $('#password');
        if (loginIdControl.val().length == 0) {
            alert('请输入登录名！');
            loginIdControl.focus();
            return false;
        } else if (pwdControl.val().length == 0) {
            alert('请输入密码！');
            pwdControl.focus();
            return false;
        } else {
            var epwd = CryptoJS.MD5(pwdControl.val());
            pwdControl.val(epwd);
            return true;
        }
    }
    </script>
</body>

</html>
