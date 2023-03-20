var table = layui.table,
	form = layui.form,
	laydate = layui.laydate;

$(function() {
	page();
})

function page() {
	table.render({
		elem: '#test'
		,url:'/daily/book/page'
		,toolbar: '#toolbarDemo'
		,title: '用户数据表'
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{field:'name', title:'名称', templet: (data)=>`<a href="/daily/book/detail/${data.id}">${data.name}</a>`}
			,{field:'publishedDate', title:'出版时间'}
			,{field:'end', title:'是否完结', templet: (res)=>`${res.end? `<span style="color: red;">是</span>`: `<span style="color: green">否</span>`}`}
			,{field:'lastUpdateTime', title:'最新更新时间', sort: true}
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
	});
}

table.on('tool(test)', function (obj) {
	if (obj.event === 'edit') { //编辑
		$.get("/daily/book/get/"+obj.data.id, function(result) {
			openWin(result.data)
		});
	}
	if (obj.event === 'setDefault') { //编辑
		$.get("/daily/book/setDefault/"+obj.data.id, function(result) {
			refresh(result);
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
	var index = layer.open({
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
				url: "/daily/book/save",
				dataBefore: function(data) {
					data.end = data.end=="on"? 1: 0
				}
				, index: index
			})
		}
	});
	$("select[name=classId]").val(model.classId);
	form.render();

	laydate.render({
		elem: "#publishedDate",
		type: "date"
	})
}
