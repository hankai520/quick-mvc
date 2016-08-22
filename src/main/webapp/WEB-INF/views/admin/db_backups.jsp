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
            $('#miBackups').addClass('active');
            <!--// 必须用JS来初始化表格，因为通过HTML属性来初始化，本地化JS的执行会滞后，因而失效 -->
            $('#dataTable').bootstrapTable({
                url: '/admin/db_backups.json',
                sidePagination: 'server',
                search: false,
                pagination: false,
                columns: [{
                    field: 'timestamp',
                    title: '日期',
                    sortable: false,
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return '<a title="下载" href="javascript:;">'+value+'</a>';
                    }
                }, {
                    field: 'fileSize',
                    title: '大小',
                    valign: 'middle',
                    sortable: false
                }, {
                    field: 'checksum',
                    title: 'SHA1 校验和',
                    valign: 'middle',
                    sortable: false
                }, {
                    title: '操作',
                    valign: 'middle',
                    sortable: false,
                    formatter: function (value, row, index) {
                        return '<a href="javascript:;" style="margin-right:6px;" class="btn btn-primary btn-sm">恢复</a> ' + 
                        '<a href="javascript:;" class="btn btn-danger btn-sm">删除</a>';
                    }
                }]
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <ol class="breadcrumb">
                <li><a href=""><span class="glyphicon glyphicon-home"></span></a></li>
                <li>数据备份</li>
            </ol>
        </div><!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="columns btn-group pull-left">
                            <a href="/admin/users/add">
                                <button class="btn btn-info" type="button" title="开始备份">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    开始备份
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