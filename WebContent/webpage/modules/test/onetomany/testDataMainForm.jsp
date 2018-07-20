<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>票务代理管理</title>
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
			            elem: '#inDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="testDataMain" action="${ctx}/test/onetomany/testDataMain/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属用户：</label></td>
					<td class="width-35">
						<sys:treeselect id="tuser" name="tuser.id" value="${testDataMain.tuser.id}" labelName="tuser.name" labelValue="${testDataMain.tuser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${testDataMain.office.id}" labelName="office.name" labelValue="${testDataMain.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属区域：</label></td>
					<td class="width-35">
						<sys:treeselect id="area" name="area.id" value="${testDataMain.area.id}" labelName="area.name" labelValue="${testDataMain.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>性别：</label></td>
					<td class="width-35">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>加入日期：</label></td>
					<td class="width-35">
						<input id="inDate" name="inDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${testDataMain.inDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">火车票：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">飞机票：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">汽车票：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChildList', testDataChildRowIdx, testDataChildTpl);testDataChildRowIdx = testDataChildRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>出发地</th>
						<th>目的地</th>
						<th>出发时间</th>
						<th>代理价格</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChildList">
				</tbody>
			</table>
			<script type="text/template" id="testDataChildTpl">//<!--
				<tr id="testDataChildList{{idx}}">
					<td class="hide">
						<input id="testDataChildList{{idx}}_id" name="testDataChildList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChildList{{idx}}_delFlag" name="testDataChildList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChildList{{idx}}_startArea" name="testDataChildList[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChildList{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChildList{{idx}}_endArea" name="testDataChildList[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChildList{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<input id="testDataChildList{{idx}}_starttime" name="testDataChildList[{{idx}}].starttime" type="text" readonly="readonly" maxlength="20" class="laydate-icon form-control layer-date  required"
							value="{{row.starttime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
					
					
					<td>
						<input id="testDataChildList{{idx}}_price" name="testDataChildList[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required"/>
					</td>
					
					
					<td>
						<textarea id="testDataChildList{{idx}}_remarks" name="testDataChildList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChildRowIdx = 0, testDataChildTpl = $("#testDataChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain.testDataChildList)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChildList', testDataChildRowIdx, testDataChildTpl, data[i]);
						testDataChildRowIdx = testDataChildRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild2List', testDataChild2RowIdx, testDataChild2Tpl);testDataChild2RowIdx = testDataChild2RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>出发地</th>
						<th>目的地</th>
						<th>代理价格</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild2List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild2Tpl">//<!--
				<tr id="testDataChild2List{{idx}}">
					<td class="hide">
						<input id="testDataChild2List{{idx}}_id" name="testDataChild2List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild2List{{idx}}_delFlag" name="testDataChild2List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild2List{{idx}}_startArea" name="testDataChild2List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild2List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild2List{{idx}}_endArea" name="testDataChild2List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild2List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<input id="testDataChild2List{{idx}}_price" name="testDataChild2List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control "/>
					</td>
					
					
					<td>
						<textarea id="testDataChild2List{{idx}}_remarks" name="testDataChild2List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild2List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild2RowIdx = 0, testDataChild2Tpl = $("#testDataChild2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain.testDataChild2List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild2List', testDataChild2RowIdx, testDataChild2Tpl, data[i]);
						testDataChild2RowIdx = testDataChild2RowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-3" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild3List', testDataChild3RowIdx, testDataChild3Tpl);testDataChild3RowIdx = testDataChild3RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>出发地</th>
						<th>目的地</th>
						<th>代理价格</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild3List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild3Tpl">//<!--
				<tr id="testDataChild3List{{idx}}">
					<td class="hide">
						<input id="testDataChild3List{{idx}}_id" name="testDataChild3List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild3List{{idx}}_delFlag" name="testDataChild3List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild3List{{idx}}_startArea" name="testDataChild3List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild3List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild3List{{idx}}_endArea" name="testDataChild3List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild3List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<input id="testDataChild3List{{idx}}_price" name="testDataChild3List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control "/>
					</td>
					
					
					<td>
						<textarea id="testDataChild3List{{idx}}_remarks" name="testDataChild3List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild3List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild3RowIdx = 0, testDataChild3Tpl = $("#testDataChild3Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain.testDataChild3List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild3List', testDataChild3RowIdx, testDataChild3Tpl, data[i]);
						testDataChild3RowIdx = testDataChild3RowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>