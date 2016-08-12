<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:frame>
    <jsp:attribute name="linkResources">
        <link href="css/bootstrap-switch.min.css" rel="stylesheet" />
    </jsp:attribute>
    <jsp:attribute name="linkScripts">
        <script src="js/crypto-js.js"></script>
    </jsp:attribute>
    <jsp:attribute name="pageDidLoad">
        <script type="text/javascript">
            $('#miUsers').addClass('active');
            
            function verify() {
                var pwdControl = $('#password');
                var pwdControl2 = $('#password2');
                if (pwdControl.val().length == 0) {
                    alert('请输入密码！');
                    pwdControl.focus();
                    return false;
                } else if (pwdControl.val() != pwdControl2.val()) {
                    alert('两次输入的密码不一致！');
                    pwdControl2.focus();
                    return false;
                } else {
                    var epwd = CryptoJS.MD5(pwdControl.val());
                    pwdControl.val(epwd);
                    epwd = CryptoJS.MD5(pwdControl2.val());
                    pwdControl2.val(epwd);
                    return true;
                }
            }
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <ol class="breadcrumb">
                <li>
                    <a href=""><span class="glyphicon glyphicon-home"></span></a>
                </li>
                <li><a href="/admin/users">用户管理</a></li>
                <li>修改用户密码</li>
            </ol>
        </div><!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <form:form method="post" action="/admin/users/${ user.id }/change_pwd" enctype="multipart/form-data" modelAttribute="user" onsubmit="return verify();">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <p>修改用户密码</p>
                        </div>
                        <div class="panel-body">
                            <c:if test="${ not empty pageMessage }">
                                <div class="alert bg-success alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                    <span class="glyphicon glyphicon-ok-sign"></span>
                                    ${ pageMessage }
                                </div>
                            </c:if>
                            <c:if test="${ not empty pageError }">
                                <div class="alert bg-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                    <span class="glyphicon glyphicon-exclamation-sign"></span>
                                    ${ pageError }
                                </div>
                            </c:if>
                            <div class="col-md-9">
                                <div class="form-group">
                                    <label>手机号</label>
                                    <form:input path="mobile" cssClass="form-control" readonly="true" />
                                </div>
                                <div class="form-group">
                                    <label>姓名</label>
                                    <form:input path="name" cssClass="form-control" readonly="true"/>
                                </div>
                                <div class="form-group">
                                    <label>新密码 <span class="fg-red">*</span></label>
                                    <form:password path="password" cssClass="form-control" maxlength="50" value="" />
                                </div>
                                <div class="form-group">
                                    <label>再次输入密码 <span class="fg-red">*</span></label>
                                    <input id="password2" type="password" class="form-control" maxlength="50" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info">提交</button>
                    <a href="/admin/users" class="btn btn-link">取消</a>
                </form:form>
            </div>
        </div><!--/.row-->
    </jsp:body>
</t:frame>