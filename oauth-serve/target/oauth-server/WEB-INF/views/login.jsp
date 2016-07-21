<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="/common/global.jsp" %>
	<title>Insert title here</title>
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
					<td><input id="username" name="username" class="login-input" placeholder="用户名（见下左表）" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">密码：</td>
					<td><input id="password" name="password" type="password" class="login-input" placeholder="默认为：000000" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">自动登录：</td>
					<td><input type="checkbox" name="rememberMe" value="true"></td>
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
		<hr />
		<div>
            <div style="float:left; width: 48%;margin-right: 2%;">
                <table border="1">
                    <caption>用户列表(密码：000000)</caption>
                    <tr>
                        <th width="50" style="text-align: center">用户名</th>
                        <th style="text-align: center">角色</th>
                    </tr>
                    <tr>
                        <td>admin</td>
                        <td>管理员、用户</td>
                    </tr>
                    <tr>
                        <td>adminUser</td>
                        <td>用户、管理员</td>
                    </tr>
                    <tr>
                        <td>hruser</td>
                        <td>人事、用户</td>
                    </tr>
                    <tr>
                        <td>leaderuser</td>
                        <td>部门经理、用户</td>
                    </tr>
                </table>
            </div>
            <div style="float:right; width: 50%;">
                   <h5>组件版本信息</h5>
                <ul>
                    <li>OAuth2：org.apache.oltu.oauth2 1.0.0</li>
                    <li>Shiro: org.apache.shiro 1.2.2</li>
					<li>Kerberos: MIT Krb5</li>
                    <li>Redis Cluster：3.0.2</li>
                    <li>Spring版本：4.0.0</li>
                    <li>Database：mysql5</li>
                    <li>Sawgger: 1.5.6</li>
                    <li>Metrics: 3.1.2</li>
                </ul>
            </div>
		</div>
        <hr />
    </div>
</body>
<script type="text/javascript">
	function doRegister(){
		debugger;
		window.location.href="${ctx}/user/create";
	}
</script>
</html>
