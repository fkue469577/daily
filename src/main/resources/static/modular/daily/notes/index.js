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
			,{title:'标题', templet: (obj)=>{
				return `<div class="p-b-c" data-index="${obj.LAY_TABLE_INDEX}">${obj.title}</div>`
				}}
			, {title: "堆叠顺序", templet: (obj)=>{
					return `<input class="p-b-z-index layui-input" lay-unrow type="number" min="0" value="${obj.zIndex}" data-index="${obj.LAY_TABLE_INDEX}"/>`
				}}
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
		, done: function (res, curr, count) {
			$(".p-b-z-index").change(function() {
				var _this = $(this);
				var id = res.data[_this.attr("data-index")].id;
				var zIndex = _this.val();
				$.post("/daily/notes/save", {id: id, zIndex: zIndex}, (res)=>refresh(res, ()=>$(".layui-laypage-btn").click()));
			});
		}
	});
	table.on("row(test)", function(obj) {
		$.get(`/daily/notes/get/${obj.data.id}`, function(result) {
			layer.open({
				type: 1,
				content: `<div class="editor-content-view">${result.data.context}</div>`,
				area: ["700px", "500px"],
				shadeClose: true
			})
			layer.photos({
				photos: ".editor-content-view"
				,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
			});
		})
	})
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
	var index = layer.open({
		type: 1,
		area: ['700px', '585px'],
		title: model.id? "编辑":"创建" + '笔记',
		shadeClose: true, //点击遮罩关闭
		content: template("tpl", {model: model})
		,btn: ['提交','取消']
		,btnAlign: 'r'
		,skin: 'layer-ext-myskin'
		,yes:function () {
			$("textarea[name=context]").val(editor.getHtml());
			$("#form").checkCommit({
				url: "/daily/notes/save"
				, index: index
			})
		}
	});

	const E = window.wangEditor
	// 切换语言
	const LANG = location.href.indexOf('lang=en') > 0 ? 'en' : 'zh-CN'
	E.i18nChangeLanguage(LANG)
	window.editor = E.createEditor({
		selector: '#editor-text-area',
		html: model.context,
		config: {
			placeholder: 'Type here...',
			MENU_CONF: {
				uploadImage: {
					fieldName: 'your-fileName',
					base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
				}
			},
			onChange(editor) {
			}
		}
	})
	var toolbar = E.createToolbar({
		editor,
		selector: '#editor-toolbar',
		config: {}
	})
	$("#editor-text-area").css("height", "260px")
}

