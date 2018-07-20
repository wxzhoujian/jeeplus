<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>票务代理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginInDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endInDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>票务代理列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="testDataMain" action="${ctx}/test/onetomany/testDataMain/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>归属用户：</span>
				<sys:treeselect id="tuser" name="tuser.id" value="${testDataMain.tuser.id}" labelName="tuser.name" labelValue="${testDataMain.tuser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>性别：</span>
				<form:radiobuttons class="i-checks" path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			<span>加入日期：</span>
				<input id="beginInDate" name="beginInDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${testDataMain.beginInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endInDate" name="endInDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${testDataMain.endInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="test:onetomany:testDataMain:add">
				<table:addRow url="${ctx}/test/onetomany/testDataMain/form" title="票务代理"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:onetomany:testDataMain:edit">
			    <table:editRow url="${ctx}/test/onetomany/testDataMain/form" title="票务代理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:onetomany:testDataMain:del">
				<table:delRow url="${ctx}/test/onetomany/testDataMain/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:onetomany:testDataMain:import">
				<table:importExcel url="${ctx}/test/onetomany/testDataMain/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:onetomany:testDataMain:export">
	       		<table:exportExcel url="${ctx}/test/onetomany/testDataMain/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column tuser.name">归属用户</th>
				<th  class="sort-column office.name">归属部门</th>
				<th  class="sort-column area.name">归属区域</th>
				<th  class="sort-column name">名称</th>
				<th  class="sort-column sex">性别</th>
				<th  class="sort-column inDate">加入日期</th>
				<th  class="sort-column remarks">备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="testDataMain">
			<tr>
				<td> <input type="checkbox" id="${testDataMain.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看票务代理', '${ctx}/test/onetomany/testDataMain/form?id=${testDataMain.id}','800px', '500px')">
					${testDataMain.tuser.name}
				</a></td>
				<td>
					${testDataMain.office.name}
				</td>
				<td>
					${testDataMain.area.name}
				</td>
				<td>
					${testDataMain.name}
				</td>
				<td>
					${fns:getDictLabel(testDataMain.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${testDataMain.inDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${testDataMain.remarks}
				</td>
				<td>
					<shiro:hasPermission name="test:onetomany:testDataMain:view">
						<a href="#" onclick="openDialogView('查看票务代理', '${ctx}/test/onetomany/testDataMain/form?id=${testDataMain.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="test:onetomany:testDataMain:edit">
    					<a href="#" onclick="openDialog('修改票务代理', '${ctx}/test/onetomany/testDataMain/form?id=${testDataMain.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="test:onetomany:testDataMain:del">
						<a href="${ctx}/test/onetomany/testDataMain/delete?id=${testDataMain.id}" onclick="return confirmx('确认要删除该票务代理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>