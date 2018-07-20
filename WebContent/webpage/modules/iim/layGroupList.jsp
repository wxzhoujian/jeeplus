<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>群组管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/css/animate.css" rel="stylesheet">
	<script type="text/javascript">
		$(document).ready(function() {
			top.$.jBox.closeTip();
		});
	    function searchFriend(layGroupId){
			top.layer.open({
			    type: 2,  
			    area: ['800px', '500px'],
			    title:"添加好友",
			    name:'friend',
			    content: "${ctx}/iim/contact/searchUsers" ,
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			    	 var ids = iframeWin.getSelectedIds();

			    	 if(ids == "-1"){
				    	 return;
			    	 }
			    	 window.location = "${ctx}/iim/layGroup/addUser?ids="+ids+"&groupid="+layGroupId;
					 top.layer.close(index);//关闭对话框。
				  },
				  cancel: function(index){ 
	    	       }
			}); 
		};

		function addGroup(){
			openDialog("创建群组","${ctx}/iim/layGroup/form","800px", "500px");

		}
	</script>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            
            <c:forEach items="${layGrouplist}" var="layGroup">
            	<div class="col-sm-4">
                <div class="ibox ">
                    <div class="ibox-title">
                    	 <a href="#"><img alt="member" class="img-circle" src="${layGroup.avatar}" width="50px" height="50px">
                            ${layGroup.groupname}</a>
                 
                        <span class=" pull-right">
                        <c:if test="${layGroup.createBy.name == fns:getUser().name}">
                        	<a href="${ctx}/iim/layGroup/delete?id=${layGroup.id}" onclick="return confirmx('确认要解散该群组吗？', this.href)"   class="btn btn-danger btn-xs"> 解散</a>
                        </c:if>
                         <c:if test="${layGroup.createBy.name != fns:getUser().name}">
                        		<a href="${ctx}/iim/layGroup/logout?user.id=${fns:getUser().id}&group.id=${layGroup.id}" onclick="return confirmx('确认要退出该群组吗？', this.href)"   class="btn btn-danger btn-xs"> 退出</a>
                        </c:if>
                        </span>
                    </div>
                    <div class="ibox-content">
                        <div class="team-members">
		
								<a href="#" style="float:left"><img  class="img-circle" src="${layGroup.createBy.photo}">
                         		${layGroup.createBy.name}(群主)</a>
                         	 <c:forEach items="${layGroup.layGroupUserList}" var="layGroupUser">
                         	 	
                         		<c:if test="${layGroup.createBy.name == fns:getUser().name}">
	                         		<div class="ibox-tools" style="float:left">
		                         	  	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><img  class="img-circle" src="${layGroupUser.user.photo}">
		                         		${layGroupUser.user.name}</a>
		                         		<ul class="dropdown-menu dropdown-user">
											<li>
											<a href="${ctx}/iim/layGroup/logout?user.id=${layGroupUser.user.id}&group.id=${layGroup.id}" onclick="return confirmx('确认要删除该成员吗？', this.href)" > 删除</a>
											</li>
										</ul>
									</div>
								</c:if>
								<c:if test="${layGroup.createBy.name != fns:getUser().name}">
		                         	  	<a href="#"><img  class="img-circle" src="${layGroupUser.user.photo}">
		                         		${layGroupUser.user.name}</a>
								</c:if>
                         	  </c:forEach>
                         	 <c:if test="${layGroup.createBy.name == fns:getUser().name}">
                         	 	<a href="#" onclick="searchFriend('${layGroup.id}')"><img  class="img-circle" src="${ctxStatic}/images/add_user.jpg">
                         			添加</a>
							</c:if>					
                        </div>
                        <h4>群组简介</h4>
                        <p>
                          ${layGroup.remarks}
                        </p>
                

                    </div>
                </div>
                
			</div>
		</c:forEach>
             <div class="col-sm-4">
                <div class="contact-box">
                    <a href="#" onclick="addGroup()">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" style="height:140px;width:140px" class="img-circle m-t-xs img-responsive" src="${ctxStatic}/images/add_user.jpg">
                                <div class="m-t-xs font-bold"></div>
                            </div>
                        </div>
                        <div class="col-sm-8">
                        	<br/>
                            <h1><strong>创建群组</strong></h1>
                        </div>
                        <div class="clearfix"></div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>