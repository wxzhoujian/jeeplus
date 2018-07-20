<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>请假表单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
					laydate({
			            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="formLeave" action="${ctx}/test/one/formLeave/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工：</label></td>
					<td class="width-35">
						<sys:treeselect id="tuser" name="tuser.id" value="${formLeave.tuser.id}" labelName="tuser.name" labelValue="${formLeave.tuser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${formLeave.office.id}" labelName="office.name" labelValue="${formLeave.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">归属区域：</label></td>
					<td class="width-35">
						<sys:treeselect id="area" name="area.id" value="${formLeave.area.id}" labelName="area.name" labelValue="${formLeave.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">请假开始日期：</label></td>
					<td class="width-35">
						<input id="beginDate" name="beginDate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${formLeave.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">请假结束日期：</label></td>
					<td class="width-35">
						<input id="endDate" name="endDate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${formLeave.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>