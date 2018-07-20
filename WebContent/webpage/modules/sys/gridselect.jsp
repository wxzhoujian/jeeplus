<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		

			    $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			    	  $('#contentTable tbody tr td input.i-checks').iCheck('check');
			    	});

			    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			    	  $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
			    	});
			    
			
		});

		function getSelectedItem(){

			var size = $("#contentTable tbody tr td input.i-checks:checked").size();
			  if(size == 0 ){
					top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
					return "-1";
				  }

			  if(size > 1 ){
					top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
					return "-1";
				  }
			    var id =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("id");
			    
				var label = $("#contentTable tbody tr td input.i-checks:checkbox:checked").parent().parent().parent().find(".codelabel").html();
				return id+"_item_"+label;
		}
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${url}");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body class="gray-bg">
	<div class="">
    <div class="ibox-content">
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="obj" action="${url}" method="post" class="form-inline">
		<input type="hidden" name="url" value="${url}"/>
		<input type="hidden" name="fieldLabels" value="${fieldLabels}"/>
		<input type="hidden" name="fieldKeys" value="${fieldKeys}"/>
		<input type="hidden" name="searchLabel" value="${searchLabel}"/>
		<input type="hidden" name="searchKey" value="${searchKey}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>${searchLabel }</span>
				<form:input path="${searchKey}" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
		
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<c:forEach items="${labelNames}" var="name"  varStatus="status">
					<th>${name}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="obj">
			<tr>
				<td> <input type="checkbox" id="${fns:getValue(obj, 'id')}" class="i-checks"></td>
				
				<c:forEach items="${labelValues}" var="value"  varStatus="status">
					<c:if test="${status.first==true}">
						<td class="codelabel">${fns:getValue(obj, value)}</td>
					</c:if>
					
					<c:if test="${status.first!=true}">
						<td>${fns:getValue(obj, value)}</td>
					</c:if>
				</c:forEach>
			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
</div>
</body>
</html>