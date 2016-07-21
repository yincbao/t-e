$(function() {
	$('#portlet-container').portlet({
		sortable: true,
		columns: [{
			width: 400,
			portlets: [{
				title: '我的授权',
				content: {
					style: {
						maxHeight: 300
					},
					type: 'ajax',
					dataType: 'json',
					url: ctx + '/api/client/approveRequests',
					formatter: function(o, pio, data) {
                        if (data.length == 0) {
                            return "无授权申请！";
                        }
						var ct = "<table><tr><th>请求者</th><th>请求资源</th><th>资源列表</th><th>操作</th></tr>";

						$.each(data, function() {
							ct += "<li>申请人:<a href='#'" + this.applicant.princ + "</a><span class='ui-state-highlight ui-corner-all'>" + this.name + "</span>";
							ct += "<span class='version' title='流程定义版本：" + this.pdversion + "'><b>V:</b>" + this.pdversion + "</span>";
							ct += "<a class='trace' href='#' pid='" + this.pid + "' title='点击查看流程图'>跟踪</a>";
							ct += "<span class='status' title='任务状态'>" + (this.status == 'claim' ? '未签收' : '') + "</span>";
							ct += "</li>";
						});
						return ct + "</ol>";
					},
					afterShow: function() {
						$('.trace').click(graphTrace);
					}
				}
			}, {
				title: '我的申请',
				content: {
					style: {
						maxHeight: 300
					},
					type: 'ajax',
					dataType: 'json',
					url: ctx + '/api/client/approveRequests',
					formatter: function(o, pio, data) {
                        if (data.length == 0) {
                            return "我等待申请！";
                        }
						var ct = "<table><tr><th>请求者</th><th>请求资源</th><th>资源列表</th><th>操作</th></tr>";

						$.each(data, function() {
							ct += "<li>申请人:<a href='#'" + this.applicant.princ + "</a><span class='ui-state-highlight ui-corner-all'>" + this.name + "</span>";
							ct += "<span class='version' title='流程定义版本：" + this.pdversion + "'><b>V:</b>" + this.pdversion + "</span>";
							ct += "<a class='trace' href='#' pid='" + this.pid + "' title='点击查看流程图'>跟踪</a>";
							ct += "<span class='status' title='任务状态'>" + (this.status == 'claim' ? '未签收' : '') + "</span>";
							ct += "</li>";
						});
						return ct + "</ol>";
					},
					afterShow: function() {
						$('.trace').click(graphTrace);
					}
				}
			}]
		}, {
			width: 800,
			portlets: [{
				title: '基于OAuth2的双向授权流程图',
				content: {
					type: 'text',
					text: function() {
						return $('.demos').html();
					}
				}
			}   ]
		}, {
			width: 800,
			portlets: [{
				title: 'Kerberos示意图',
				content: {
					type: 'text',
					text: function() {
						return "";
					}
				}
			}   ]
		}]
	});
});

function renderAppli(client){

	var tmp = "<tr><td>"+client.applicant.princ+"</td><td>" + client.ar.name +"</td><td>";
	var car = client.ar.authorizationsToResource;
	for(var i=0;i<car.length;i++){
		tmp+=car.resource+"<br>";
	}
	tmp+="</td><td><a href='#' onclick=dealWith('"+client.id+"','0')>允许</a> <a href='#' onclick=dealWith('"+client.id+"','1')>拒绝</a>";
}

function dealWith(aid,result){
		window.location.href="${pageContext.request.contextPath}/client/"+aid+"/deal?result="+result;
}