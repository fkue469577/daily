var table = layui.table,
	form = layui.form,
	formSelects = layui.formSelects;

$(function() {
	page();
})

function page() {
	table.render({
		elem: '#test'
		,url:'/system/manager/page'
		,toolbar: '#toolbarDemo'
		,title: '用户数据表'
		, where: $("#searchForm").serializeObject()
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{type: 'numbers', fixed: '序号'}
			,{field:'name', title:'姓名', }
			,{field:'userName', title:'用户名',  }
			,{field:'roleName', title:'角色'}
			,{ title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,id: "testReload"
		,page: true
	});
}

table.on('tool(test)', function (obj) {
	var data = obj.data;
	if (obj.event === 'del') { //删除
		layer.confirm('是否确认删除？', function (index) {
			$.get("/system/manager/del/"+obj.data.id, function (result) {
				refresh(result);
			})
		});
	} else if (obj.event === 'edit') { //编辑
		$.get("/system/manager/get/"+obj.data.id, function(result) {
			openWin(result.data)
		});
	}
});
table.on("toolbar(test)", function(obj) {
	switch(obj.event) {
		case 'create':
			openWin({})
			break;
	}
});

function openWin(model) {
	layer.open({
		type: 1,
		area: ['550px', 'auto'],
		title: (model.id? "编辑": "新增") + '用户',
		shadeClose: true, //点击遮罩关闭
		content: template("tpl", {model: model})
		,btn: ['提交','取消']
		,btnAlign: 'r'
		,skin: 'layer-ext-myskin'
		,yes:function () {
			$("#form").checkCommit({
				url: "/system/manager/save",

			})
		}
	});
	form.render();
	formSelects.render();
	formSelects.value("roleId", model.roleId? model.roleId.split(","): [])
}
