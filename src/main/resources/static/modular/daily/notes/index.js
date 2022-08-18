var table = layui.table,
	form = layui.form,
	laydate = layui.laydate;

$(function() {
	page();
})

function page() {
	table.render({
		elem: '#test'
		,url:'/daily/notes/page'
		,toolbar: '#toolbarDemo'
		,title: '用户数据表'
		, where: $("#searchForm").serializeObject()
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{type:'checkbox'}
			,{field:'title', title:'标题'}
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
	});
}

table.on('tool(test)', function (obj) {
	if (obj.event === 'edit') { //编辑
		$.get("/daily/notes/get/"+obj.data.id, function(result) {
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
		area: ['700px', '585px'],
		title: model.id? "编辑":"创建" + '笔记',
		shadeClose: true, //点击遮罩关闭
		content: template("tpl", {model: model})
		,btn: ['提交','取消']
		,btnAlign: 'r'
		,skin: 'layer-ext-myskin'
		,yes:function () {
			$("#form").checkCommit({
				url: "/daily/notes/save",
			})
		}
	});

	UE.delEditor('context')
	var editor = UE.getEditor('context',{
		//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
		toolbars:[
			['fullscreen', 'source', 'undo', 'redo'],
			['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', "insertcode", 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
		],
		//focus时自动清空初始化时的内容
		autoClearinitialContent:true,
		autoHeightEnabled: false,
		zIndex: 19891019,
		//关闭字数统计
		wordCount:false,
		//关闭elementPath
		elementPathEnabled:false,
		//默认的编辑区域高度
		initialFrameHeight:300
		//更多其他参数，请参考ueditor.config.js中的配置项
	})
	editor.ready(function() {
		editor.setContent(model.context);
	});
}

