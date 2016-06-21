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
            $('#miApps').addClass('active');
            <!--// 必须用JS来初始化表格，因为通过HTML属性来初始化，本地化JS的执行会滞后，因而失效 -->
            $('#sampleTable').bootstrapTable({
                url: 'admin/apps.json',
                sidePagination: 'server',
                showRefresh: true,
                pageList: [10,20,50,100],
                showColumns: true,
                search: true,
                pagination: true,
                columns: [{
                    field: 'id',
                    title: 'ID',
                    sortable: 'true',
                    valign: 'middle'
                }, {
                    field: 'name',
                    title: '名称',
                    valign: 'middle',
                    sortable: 'true',
                    formatter: function (value, row, index) {
                        return '<a href="admin/apps/' + row.id + '/edit">' + value + '</a>';
                    }
                }, {
                    field: 'platformDesc',
                    title: '平台',
                    valign: 'middle',
                    sortable: 'false'
                }, {
                    field: 'version',
                    title: '版本',
                    valign: 'middle',
                    sortable: 'true'
                }, {
                    field: 'bundleIdentifier',
                    title: '程序包ID',
                    valign: 'middle',
                    sortable: 'false'
                }, {
                    field: 'statusDesc',
                    title: '状态',
                    valign: 'middle',
                    sortable: 'false'
                }]
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <ol class="breadcrumb">
                <li><a href=""><span class="glyphicon glyphicon-home"></span></a></li>
                <li>数据管理</li>
            </ol>
        </div><!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="columns btn-group pull-left">
                            <a href="#">
                                <button class="btn btn-info" type="button" title="添加数据">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    添加数据
                                </button>
                            </a>
                        </div>
                    </div>
                    <div class="panel-body">
                        <table id="sampleTable"></table>
                    </div>
                </div>
            </div>
        </div><!--/.row-->
    </jsp:body>
</t:frame>