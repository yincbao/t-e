<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="/common/global.jsp" %>
	<title>login</title>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <style type="text/css">
        .login-center {
            width: 600px;
            margin-left:auto;
            margin-right:auto;
        }
        #loginContainer {
            margin-top: 3em;
        }
        .login-input {
            padding: 4px 6px;
            font-size: 14px;
            vertical-align: middle;
        }
    </style>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	$(function() {
		$('button').button({
			icons: {
				primary: 'ui-icon-key'
			}
		});
	});
	</script>
</head>
<body>
<div id="loginContainer" class="login-center">
	<c:if test="${not empty error}">
            <h2 id="error" class="alert alert-error">用户名或密码错误！！！</h2>
        </c:if>
        <c:if test="${not empty timeout}">
            <h2 id="error" class="alert alert-error">未登录或超时！！！</h2>
        </c:if>
<div style="text-align: center;">
            <img src="${ctx }/images/logo.png" height="48" align="top"  style="margin-top:5px" /><br>
			统一认证平台
		</div>
		<hr />
<form action="" method="post">
			<table>
				<tr>
					<td width="200" style="text-align: right;">用户名：</td>
					<td><input type="text" name="username" value=""></td>
				</tr>
				<tr>
					<td width="200" style="text-align: right;">密码：</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr><td>&nbsp;</td>
					<td>
						<button type="submit">登录</button>
					&nbsp;&nbsp;&nbsp;
						<button type="button" id="reg_btn" value="注册" onclick="doRegister();">注册</button>
					</td>
				</tr>
			</table>		
</form>
 </div>
</body>
<script type="text/javascript">
	function doRegister(){
		debugger;
		window.location.href="${ctx}/user/create";
	}
</script>
</html>