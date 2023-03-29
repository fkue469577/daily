var table = layui.table,
	form = layui.form,
	laydate = layui.laydate;

$(function() {
	page();
})

function page() {
	table.render({
		elem: '#test'
		,url:'/daily/book/notes/page'
		,toolbar: '#toolbarDemo'
		,title: '用户数据表'
		, where: $("#searchForm").serializeObject()
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{field:'bookName', title:'书本名称'}
			,{field:'chapterName', title:'章节'}
			,{title:'标题', templet: (obj)=>{
					return `<div class="p-b-c" data-index="${obj.LAY_TABLE_INDEX}">${obj.title}</div>`
				}}
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
		, done: function (res, curr, count) {
			$(".p-b-c").click(function() {
				var _this = $(this);
				var data = res.data[_this.attr("data-index")]
				var context = data.context
				layer.open({
					type: 1,
					content: `<div class="editor-content-view">${context}</div>`,
					area: ["700px", "500px"],
					shadeClose: true
				})
				layer.photos({
					photos: ".editor-content-view"
					,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
				});
			});
		}
	});
}

table.on('tool(test)', function (obj) {
	if (obj.event === 'edit') { //编辑
		$.get("/daily/book/notes/get/"+obj.data.id, function(result) {
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
		area: ['700px', '570px'],
		title: model.id? "编辑":"创建" + '章节笔记',
		shadeClose: true, //点击遮罩关闭
		content: template("tpl", {model: model})
		,btn: ['提交','取消']
		,btnAlign: 'r'
		,skin: 'layer-ext-myskin'
		,yes:function () {
			$("textarea[name=context]").val(editor.getHtml());
			$("#form").checkCommit({
				url: "/daily/book/notes/save"
				, index: index
			})
		}
	});
	if(model.bookId) $("select[name=bookId]").val(model.bookId);
	else $("select[name=bookId] option[isDefault=true]").attr("selected", "true");
	form.render();

	const E = window.wangEditor
	// 切换语言
	const LANG = location.href.indexOf('lang=en') > 0 ? 'en' : 'zh-CN'
	E.i18nChangeLanguage(LANG)
	var editor = E.createEditor({
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

	var value = [{value: model.chapterId, name: model.chapterName??""}];
	var bookId = $("#form select[name=bookId]").val();
	loadChapter(bookId, value);

	form.on("select(bookId)", function(res) {
		loadChapter(res.value, value);
	});
}

function loadChapter(bookId, value) {
	$.get(`/daily/book/chapter/listByBookId/${bookId}`, function(res) {
		var chapter = xmSelect.render({
			el: '#chapterId',
			model: { label: { type: 'text' } },
			tree: {
				show: true,
				strict: false,
				expandedKeys: [ -1 ],
			},
			style: {"z-index": 1000},
			name: "chapterId",
			height: 'auto',
			data: res.data,
			on: function(data){
				if(data.isAdd){
					return data.change.slice(0, 1)
				}
			},
		})
		chapter.setValue(value, null, true);
	});
}


