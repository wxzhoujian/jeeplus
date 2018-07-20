<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ include file="/webpage/include/echarts.jsp"%>

	<div id="pie"  class="main000"></div>
	<echarts:pie
	    id="pie"
		title="某站点用户访问来源" 
		subtitle="纯属虚构"
		orientData="${orientData}"/>