<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>手工创建</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
	 <link href="${ctx }/css/main.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function() {
    	$('#startTime,#endTime').datetimepicker({
            stepMinute: 5
        });
		
		document.getElementById("metaFile").onchange = function () {
		document.getElementById("uploadFile").value = this.value;
		};
    });
    </script>
</head> 
<body>
	<div class="container showgrid">
	  <c:if test="${not empty msg}">
            <h2 id="error" class="alert alert-error">${msg}</h2>
     </c:if>
    <form:form method="post" commandName="client" enctype="multipart/form-data" class="form-horizontal">
        <form:hidden path="id"/>
        <form:hidden path="clientId"/>
        <form:hidden path="clientSecret"/>

        <table border="1">
			<tr>
				<td> <form:label path="clientName">Resource Name：</form:label></td>
				<td>
					<form:input path="clientName"/>
				</td>
			</tr>
           <tr>
				<td>  Realm Name：</td>
				<td>
					<select id="id_SEL_REALM" name="clientRealm.realm">
						<option value="HOPERUN-KERBEROS.COM" selected="true">Kerberos</option>
						<option value="IDP.HOPERUN.COM">IDP</option>
						<option value="ACT.HOPERUN.COM">ACT</option>
					</select>
				</td>
			</tr>
			  <c:if test="${op eq '新增'}">
				<tr>
					<td>Authority Meta Form:</td>
					<td>
						<input id="metaFile" name="metaFile" type="file" class="upload" />
					</td>
				</tr>
			</c:if>
            
			<tr>
				<td colspan=2 style="text-align: center;"><form:button class="btn1">${op}</form:button></td>
				
			</tr>
        </table>
		 <c:if test="${op eq '新增'}">
		You Can Download A Sample File, <a href="${pageContext.request.contextPath}/client/sample">click here</a> !
      </c:if>
    </form:form>

</div>

</body>
</html>