<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>授权</title>
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
		
	});
    </script>
</head> 
<body>

	<div>The Third Party [${client.clientName }] Is Asking You For
		These Authorities:</div>
	<div class="error">${error}</div>

	<form action="" method="post" id="form">
	<input type="hidden" name="cfmRes" id="cfmRes"/>
		<table width="100%" class="need-border" >
			<thead>
				<tr>
					<th>权限类型</th>
					<th>级别</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${clientARs}" var="ars">
					<tr>
						<td>${ars.resource}</td>
						<td><c:choose>
								<c:when test="${ars.operation<7}">
							只读
						</c:when>
								<c:otherwise>
						 	读写
						 </c:otherwise>
							</c:choose></td>
						<%-- <td><c:choose>
								<c:when test="${ars.isRequired}">
									<input type="radio" class="basx" name="${ars.id }req"
										value="${ars.id }" checked disabled /> Agree
							<input type="radio" class="basx" name="req" value="${ars.id }"
										disabled /> Deny
						</c:when>
								<c:otherwise>
									<input type="radio" class="basx" name="${ars.id }req"
										value="${ars.id }" checked /> Agree
							<input type="radio" class="basx" name="${ars.id }req"
										value="${ars.id }" /> Deny
						 </c:otherwise>
							</c:choose></td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		    <input type="button" onclick="sumfrm('auth')"value="授权"/><input type="button" onclick="sumfrm('deny')"value="拒绝"/>

	</form>
</body>
<script type="text/javascript">
debugger;
	function sumfrm(res){
		this.form.cfmRes.value=res;
		this.form.submit();
	}
</script>
</html>