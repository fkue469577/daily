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
		, where: $("#searchForm").serializeObject()
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{field:'bookName', title:'书本名称'}
			,{field:'parentName', title:'父章节'}
			,{field:'seq', title:'序号'}
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
	var index = layer.open({
		type: 1,
		area: ['550px', 'auto'],
		title: model.id? "编辑":"创建" + '章节',
		shadeClose: true, //点击遮罩关闭
		content: template("tpl", {model: model})
		,btn: ['提交','取消']
		,btnAlign: 'r'
		,skin: 'layer-ext-myskin'
		,yes:function () {
			$("#form").checkCommit({
				url: "/daily/book/chapter/save"
				, index: index
			})
		}
	});
	if(model.bookId) $("select[name=bookId]").val(model.bookId);
	else $("select[name=bookId] option[isDefault=true]").attr("selected", "true");
	form.render();

	var value = [{value: model.parentId, name: model.parentName??""}];
	var bookId = $("#form select[name=bookId]").val();
	loadChapter(bookId, value)

	form.on("select(bookId)", function(res) {
		loadChapter(res, value);
	});
}

function loadChapter(bookId, value) {
	$.get(`/daily/book/chapter/listByBookId/${bookId}`, function(res) {
		var chapter = xmSelect.render({
			el: '#parentId',
			model: { label: { type: 'text' } },
			tree: {
				show: true,
				strict: false,
				expandedKeys: [ -1 ],
			},
			name: "parentId",
			height: 'auto',
			data: res.data,
			on: function(data){
				if(data.isAdd){
					return data.change.slice(0, 1)
				}
			},
		})
		chapter.setValue(value)
	});
}
