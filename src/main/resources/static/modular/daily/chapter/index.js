var table = layui.table,
	form = layui.form,
	laydate = layui.laydate;

$(function() {
	page();
})

function page() {
	table.render({
		elem: '#test'
		,url:'/daily/book/chapter/page'
		,toolbar: '#toolbarDemo'
		,title: '用户数据表'
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{field:'bookName', title:'书本名称'}
			,{field:'parentName', title:'父章节'}
			,{field:'name', title:'章节名称'}
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
	});
}

table.on('tool(test)', function (obj) {
	if (obj.event === 'edit') { //编辑
		$.get("/daily/book/chapter/get/"+obj.data.id, function(result) {
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
				url: "/daily/book/chapter/save",
			})
		}
	});
	$("select[name=classId]").val(model.classId);
	form.render();

	loadParent($("select[name=bookId]").val())
	form.on("select(bookId)", function(obj) {
		loadParent(obj.value)
	});
}

function loadParent(bookId) {
	$.get("/daily/book/chapter/listByBookId", {bookId: bookId}, function(res) {
		$("select[name=parentId]").html(`<option value="">无</option>`+res.data.map(e=>`<option value="${e.id}">${e.seq} ${e.name}</option>`));
		form.render();
	});
}