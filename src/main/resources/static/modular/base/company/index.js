var table = layui.table,
	form = layui.form;

$(function() {
	page();
})

function page() {
	table.render({
		elem: '#test'
		,url:'/system/company/page'
		,toolbar: '#toolbarDemo'
		,title: '用户数据表'
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{field:'name', title:'名称'}
			,{field:'contacts', title:'联系人'}
			,{field:'phone', title:'联系人电话'}
			,{field:'address', title:'地址'}
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
	});
}

table.on('tool(test)', function (obj) {
	if (obj.event === 'del') { //删除
		layer.confirm('是否确认删除？', function (index) {
			$.get("/system/company/remove/"+obj.data.id, function (result) {
				refresh(result);
			})
		});
	} else if (obj.event === 'edit') { //编辑
		$.get("/system/company/get/"+obj.data.id, function(result) {
			openWin(result.data)
		});
	}
});
//头工具栏事件
table.on('toolbar(test)', function(obj){
	var checkStatus = table.checkStatus(obj.config.id);
	switch(obj.event){
		case 'create':
			openWin({})

			break;
	};
});
function openWin(model) {
	layer.open({
		type: 1,
		area: ['550px', 'auto'],
		title: model.id? "编辑":"创建" + '责任主体',
		shadeClose: true, //点击遮罩关闭
		content: template("tpl", {model: model})
		,btn: ['提交','取消']
		,btnAlign: 'r'
		,skin: 'layer-ext-myskin'
		,yes:function () {
			$("#form").checkCommit({
				url: "/system/company/save",
			})
		}
	});
	$("select[name=classId]").val(model.classId);
	form.render();
}
