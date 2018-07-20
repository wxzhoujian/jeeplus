<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>商品列表 </h5>
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
	<form:form id="searchForm" modelAttribute="goods" action="${ctx}/test/grid/goods/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>商品名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>所属类型：</span>
				<sys:gridselect url="${ctx}/test/grid/goods/selectcategory" id="category" name="category"  value="${goods.category.id}"  title="选择所属类型" labelName="category.name" 
					labelValue="${goods.category.name}" cssClass="form-control required" fieldLabels="商品分类|备注" fieldKeys="name|remarks" searchLabel="分类名" searchKey="name" ></sys:gridselect>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="test:grid:goods:add">
				<table:addRow url="${ctx}/test/grid/goods/form" title="商品"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:grid:goods:edit">
			    <table:editRow url="${ctx}/test/grid/goods/form" title="商品" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:grid:goods:del">
				<table:delRow url="${ctx}/test/grid/goods/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:grid:goods:import">
				<table:importExcel url="${ctx}/test/grid/goods/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="test:grid:goods:export">
	       		<table:exportExcel url="${ctx}/test/grid/goods/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">商品名称</th>
				<th  class="sort-column category.id">所属类型</th>
				<th  class="sort-column price">价格</th>
				<th  class="sort-column remarks">备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goods">
			<tr>
				<td> <input type="checkbox" id="${goods.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看商品', '${ctx}/test/grid/goods/form?id=${goods.id}','800px', '500px')">
					${goods.name}
				</a></td>
				<td>
					${goods.category.name}
				</td>
				<td>
					${goods.price}
				</td>
				<td>
					${goods.remarks}
				</td>
				<td>
					<shiro:hasPermission name="test:grid:goods:view">
						<a href="#" onclick="openDialogView('查看商品', '${ctx}/test/grid/goods/form?id=${goods.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="test:grid:goods:edit">
    					<a href="#" onclick="openDialog('修改商品', '${ctx}/test/grid/goods/form?id=${goods.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="test:grid:goods:del">
						<a href="${ctx}/test/grid/goods/delete?id=${goods.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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