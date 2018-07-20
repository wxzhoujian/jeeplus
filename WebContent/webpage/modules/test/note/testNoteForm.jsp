<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>富文本测试管理</title>
	<meta name="decorator" content="default"/>
	 <link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	 <link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	 <script src="${ctxStatic}/summernote/summernote.min.js"></script>
	 <script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#contents").val($("#rich_contents").next().find(".note-editable").html());//取富文本的值
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
			
				//富文本初始化
			$('#rich_contents').summernote({
                lang: 'zh-CN'
            });

			$("#rich_contents").next().find(".note-editable").html(  $("#contents").val());

			$("#rich_contents").next().find(".note-editable").html(  $("#rich_contents").next().find(".note-editable").text());
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="testNote" action="${ctx}/test/note/testNote/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">内容：</label></td>
					<td class="width-35">
						<form:hidden path="contents"/>
						<div id="rich_contents">
                           

                        </div>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>