<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
</head>
<body>

<c:if test="${not empty msg}">
    <div class="message">${msg}</div>
</c:if>

<table class="table">
    <thead>
        <tr>
            <th>请求者</th>
            <th>请求资源</th>
            <th>资源列表</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${reqList}" var="client">
            <tr>
                <td>${client.applicant.princ}</td>
                <td>${client.ar.name}</td>
                <td>
                 <c:forEach items="${client.ar.authorizationsToResource}" var="car">
                 	${car.resource} <br/>
                 </c:forEach>
                 </td>
                <td>
                    <a href="#" onclick="dealWith('${client.id}','0')">允许</a>
                    <a href="#" onclick="dealWith('${client.id}','1')">拒绝</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<script type="text/javascript">
	function dealWith(aid,result){
		window.location.href="${pageContext.request.contextPath}/client/"+aid+"/deal?result="+result;
	}
</script>
</body>
</html>