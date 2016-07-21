<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/meta.jsp"%>

<%@ include file="/common/include-base-styles.jsp"%>
<%@ include file="/common/include-jquery-ui-theme.jsp"%>
<link
	href="${ctx }/js/common/plugins/jui/extends/portlet/jquery.portlet.min.css?v=1.1.2"
	type="text/css" rel="stylesheet" />
<link href="${ctx }/js/common/plugins/qtip/jquery.qtip.css?v=1.1.2"
	type="text/css" rel="stylesheet" />
<%@ include file="/common/include-custom-styles.jsp"%>
<style type="text/css">
.template {
	display: none;
}

.version {
	margin-left: 0.5em;
	margin-right: 0.5em;
}

.trace {
	margin-right: 0.5em;
}

.center {
	width: 1200px;
	margin-left: auto;
	margin-right: auto;
}
</style>

<script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
<script
	src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js"
	type="text/javascript"></script>
<script
	src="${ctx }/js/common/plugins/jui/extends/portlet/jquery.portlet.pack.js?v=1.1.2"
	type="text/javascript"></script>
<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js"
	type="text/javascript"></script>
<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js"
	type="text/javascript"></script>
<script src="${ctx }/js/module/main/welcome-portlet.js"
	type="text/javascript"></script>
</head>
<body style="margin-top: 1em;">
	<div class="center">
		<div style="text-align: center;">
			<h3>Greetings, welcome to Hoperun Identify Provider My Process
				Id is ${pid}</h3>
		</div>
		<div id='portlet-container'></div>
	</div>

	<div class="demos template">
		<li>启动事件与结束事件</li>
		<li>边界与中间事件</li>
		<li>顺序流</li>
		<li>User task</li>
		<li>Script task</li>
		<li>Service task</li>
		<li>Business rule task</li>
		<li>Mail task</li>
		<li>Receive task</li>
		<li>Manual task</li>
		<li>排他网关</li>
		<li>并行网关</li>
		<li>包容网关</li>
		<li>事件网关</li>
		<li>普通表单</li>
		<li>动态表单</li>
		<li>外置表单</li>
		<li>ExecutionListener</li>
		<li>TaskListener</li>
		<li>Activiti REST API</li>
		<li>Activiti explorer Modeler 集成</li>
	</div>
</body>
</html>
