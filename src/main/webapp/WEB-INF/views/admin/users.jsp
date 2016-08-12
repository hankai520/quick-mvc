<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:frame>
    <jsp:attribute name="linkResources">
        <link href="css/bootstrap-table.min.css" rel="stylesheet" />
    </jsp:attribute>
    <jsp:attribute name="linkScripts">
        <script type="text/javascript" src="js/bootstrap-table.min.js"><!--//--></script>
        <script type="text/javascript" src="js/bootstrap-table-zh-CN.min.js"><!--//--></script>
    </jsp:attribute>
    <jsp:attribute name="pageDidLoad">
        <script type="text/javascript">
            $('#miMobileUsers').addClass('active');
            <!--// 必须用JS来初始化表格，因为通过HTML属性来初始化，本地化JS的执行会滞后，因而失效 -->
            $('#dataTable').bootstrapTable({
                url: 'admin/users.json',
                sidePagination: 'server',
                showRefresh: true,
                pageSize: 50,
                pageList: [50,100,150],
                showColumns: true,
                search: true,
                pagination: true,
                columns: [{
                    field: 'id',
                    title: 'ID',
                    sortable: false,
                    valign: 'middle'
                }, {
                    field: 'mobile',
                    title: '手机号',
                    valign: 'middle',
                    sortable: false,
                    formatter: function (value, row, index) {
                        return '<a href="/admin/users/' + row.id + '/edit">' + value + '</a>';
                    }
                }, {
                    field: 'name',
                    title: '姓名',
                    valign: 'middle',
                    sortable: true
                }, {
                    field: 'roleName',
                    title: '角色',
                    valign: 'middle',
                    sortable: false
                }, {
                    field: 'createTime',
                    title: '创建时间',
                    valign: 'middle',
                    sortable: false
                }, {
                    field: 'updateTime',
                    title: '最近更新',
                    valign: 'middle',
                    sortable: false
                }, {
                    field: 'statusName',
                    title: '状态',
                    valign: 'middle',
                    sortable: false
                }, {
                    field: 'deviceToken',
                    title: '设备Token',
                    valign: 'middle',
                    sortable: false,
                    visible: false
                }, {
                    title: '操作',
                    valign: 'middle',
                    sortable: false,
                    formatter: function (value, row, index) {
                        var currentRole = ${current_user.role.value()};
                        var currentUid = ${current_user.id};
                        if (row.role == 1 || currentRole == 2 || currentUid == row.id) {
                            return '<a href="/admin/users/' + row.id + '/change_pwd">修改密码</a>';
                        } else {
                            return '-';
                        }
                    }
                }]
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <ol class="breadcrumb">
                <li><a href=""><span class="glyphicon glyphicon-home"></span></a></li>
                <li>用户管理</li>
            </ol>
        </div><!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="columns btn-group pull-left">
                            <a href="/admin/users/add">
                                <button class="btn btn-info" type="button" title="添加用户">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    添加用户
                                </button>
                            </a>
                        </div>
                    </div>
                    <div class="panel-body">
                        <table id="dataTable"></table>
                    </div>
                </div>
            </div>
        </div><!--/.row-->
    </jsp:body>
</t:frame>