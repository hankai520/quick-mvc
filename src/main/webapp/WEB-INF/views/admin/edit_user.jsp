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
        <script type="text/javascript" src="js/jquery.ui.widget.js"><!--//--></script>
        <script type="text/javascript" src="js/jquery.iframe-transport.js"><!--//--></script>
        <script type="text/javascript" src="js/jquery.fileupload.js"><!--//--></script>
        <script type="text/javascript" src="js/jquery.fileupload-process.js"><!--//--></script>
        <script type="text/javascript" src="js/jquery.fileupload-validate.js"><!--//--></script>
        <script type="text/javascript" src="js/bootstrap-switch.min.js"><!--//--></script>
    </jsp:attribute>
    <jsp:attribute name="pageDidLoad">
        <script type="text/javascript">
            $('#miUsers').addClass('active');
            
            function showWarnings() {
                $('#confirmModal').modal('show');
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
                <li>编辑用户</li>
            </ol>
        </div><!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <form:form method="post" action="/admin/users/${ user.id }/edit" enctype="multipart/form-data" modelAttribute="user">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <p>编辑用户</p>
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
                                    <label>手机号 <span class="fg-red">*</span></label>
                                    <form:input path="mobile" cssClass="form-control" maxlength="20" />
                                    <form:errors cssClass="field-error" path="mobile" />
                                </div>
                                <div class="form-group">
                                    <label>姓名</label>
                                    <form:input path="name" cssClass="form-control" maxlength="20"/>
                                    <form:errors cssClass="field-error" path="name" />
                                </div>
                                <div class="form-group">
                                    <label>设备 Token</label>
                                    <form:input path="deviceToken" cssClass="form-control" maxlength="100"/>
                                    <form:errors cssClass="field-error" path="deviceToken" />
                                </div>
                                <div class="form-group">
                                    <label>角色 <span class="fg-red">*</span></label>
                                    <form:select path="role" cssClass="form-control">
                                        <c:choose>
                                            <c:when test="${ user.role.value() != 2 }">
                                                <form:option value="MobileUser"><fmt:message key="user.role.1" /></form:option>
                                                <form:option value="Operator"><fmt:message key="user.role.0" /></form:option>
                                            </c:when>
                                            <c:otherwise>
                                                <form:option value="SuperAdmin"><fmt:message key="user.role.2" /></form:option>
                                            </c:otherwise>
                                        </c:choose>
                                    </form:select>
                                    <form:errors cssClass="field-error" path="role" />
                                </div>
                                <div class="form-group">
                                    <label>状态 <span class="fg-red">*</span></label>
                                    <form:select path="status" cssClass="form-control">
                                        <form:option value="Enabled"><fmt:message key="user.status.1" /></form:option>
                                        <form:option value="Disabled"><fmt:message key="user.status.0" /></form:option>
                                    </form:select>
                                    <form:errors cssClass="field-error" path="status" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info">提交</button>
                    <a href="/admin/users" class="btn btn-link">取消</a>
                    <button type="button" class="btn btn-danger pull-right" onclick="showWarnings();">删除</button>
                </form:form>
            </div>
        </div><!--/.row-->
        <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="titleLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="titleLabel">
                           <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> 确认删除
                        </h4>
                    </div>
                    <div class="modal-body">
                        确定要删除此用户吗？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <a href="/admin/users/${ user.id }/delete" class="btn btn-danger">删除</a>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:frame>