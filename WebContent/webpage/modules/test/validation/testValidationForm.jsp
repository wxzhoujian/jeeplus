<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>测试校验管理</title>
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
			            elem: '#newDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="testValidation" action="${ctx}/test/validation/testValidation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">浮点数字：</label></td>
					<td class="width-35">
						<form:input path="num" htmlEscape="false"   max="69.3"  min="20.1" class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>整数：</label></td>
					<td class="width-35">
						<form:input path="num2" htmlEscape="false"   max="30"  min="10" class="form-control required digits"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>字符串：</label></td>
					<td class="width-35">
						<form:input path="str" htmlEscape="false" maxlength="65"  minlength="5"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">邮件：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false" maxlength="60"  minlength="10"   class="form-control  email"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">网址：</label></td>
					<td class="width-35">
						<form:input path="url" htmlEscape="false" maxlength="30"  minlength="10"   class="form-control  url"/>
					</td>
					<td class="width-15 active"><label class="pull-right">日期：</label></td>
					<td class="width-35">
						<input id="newDate" name="newDate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${testValidation.newDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>