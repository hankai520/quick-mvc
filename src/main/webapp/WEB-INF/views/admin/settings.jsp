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
            $('#miSettings').addClass('active');
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <ol class="breadcrumb">
                <li><a href=""><span class="glyphicon glyphicon-home"></span></a></li>
                <li>系统信息</li>
            </ol>
        </div><!--/.row-->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <table data-toggle="table">
                            <thead></thead>
                            <tbody>
                                <tr>
                                    <td>服务器名</td>
                                    <td>${ runtime.hostName }</td>
                                </tr>
                                <tr>
                                    <td>服务器 IP 地址</td>
                                    <td>${ runtime.ipAddress }</td>
                                </tr>
                                <tr>
                                    <td>服务器操作系统</td>
                                    <td>${ runtime.osName } ${ runtime.osVersion }, ${ runtime.osArchitecture }</td>
                                </tr>
                                <tr>
                                    <td rowspan="3">Java 信息</td>
                                    <td>版本：${ runtime.javaVersion }</td>
                                </tr>
                                <tr>
                                    <td>供应商：${ runtime.javaVender }</td>
                                </tr>
                                <tr>
                                    <td>安装路径：${ runtime.javaHome }</td>
                                </tr>
                                <tr>
                                    <td rowspan="2">Java 规范</td>
                                    <td>版本：${ runtime.jvmSpecName } ${ runtime.jvmSpecVersion }</td>
                                </tr>
                                <tr>
                                    <td>供应商：${ runtime.jvmSpecVender }</td>
                                </tr>
                                <tr>
                                    <td rowspan="4">JVM 信息</td>
                                    <td>版本：${ runtime.jvmName } ${ runtime.jvmVersion }</td>
                                </tr>
                                <tr>
                                    <td>供应商：${ runtime.jvmVender }</td>
                                </tr>
                                <tr>
                                    <td>可用总内存 / 剩余内存：${ runtime.jvmTotalMemory } / ${ runtime.jvmFreeMemory }</td>
                                </tr>
                                <tr>
                                    <td>可用处理器数：${ runtime.jvmAvailableProcessors }</td>
                                </tr>
                                <tr>
                                    <td rowspan="2">JRE 规范</td>
                                    <td>版本：${ runtime.jreSpecName } ${ runtime.jreSpecVersion }</td>
                                </tr>
                                <tr>
                                    <td>供应商：${ runtime.jreSpecVender }</td>
                                </tr>
                            </tbody>
                        </table>
                        
                    </div>
                </div>
            </div>
        </div><!--/.row-->
    </jsp:body>
</t:frame>